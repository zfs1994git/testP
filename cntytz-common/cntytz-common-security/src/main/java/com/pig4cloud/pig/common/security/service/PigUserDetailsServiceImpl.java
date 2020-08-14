/*
 *
 *  *  Copyright (c) 2019-2020, 冷冷 (wangiegie@gmail.com).
 *  *  <p>
 *  *  Licensed under the GNU Lesser General Public License 3.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *  <p>
 *  * https://www.gnu.org/licenses/lgpl.html
 *  *  <p>
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.pig4cloud.pig.common.security.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.pig4cloud.pig.admin.api.dto.CommunistUserInfo;
import com.pig4cloud.pig.admin.api.dto.UserInfo;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.admin.api.feign.RemoteCommunistService;
import com.pig4cloud.pig.admin.api.feign.RemoteUserService;
import com.pig4cloud.pig.common.core.constant.CacheConstants;
import com.pig4cloud.pig.common.core.constant.CommonConstants;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.R;
import io.micrometer.core.instrument.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户详细信息
 *
 * @author lengleng
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PigUserDetailsServiceImpl implements UserDetailsService {
    private static final String SYS_TYPE_COMMUNIST = "communist";
    private final RemoteUserService remoteUserService;
    private final RemoteCommunistService remoteCommunistService;
    private final CacheManager cacheManager;

    /**
     * 用户密码登录
     *
     * @param username 用户名
     * @return
     */
    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String sysType = request.getParameter("sysType");
        String cacheKey = String.format("%s:%s", CacheConstants.USER_DETAILS, SYS_TYPE_COMMUNIST);
        Cache cache = cacheManager.getCache(cacheKey);
        //党群缴费
        UserDetails userDetails = null;
        if (StrUtil.equals(sysType, SYS_TYPE_COMMUNIST)) {
            if (cache != null && cache.get(username) != null) {
                PigUser pigUser = (PigUser) cache.get(username).get();
                log.info("communist userDetails 存在缓存，username={} pigUser={}", username, JSONUtil.toJsonStr(pigUser));
                return pigUser;
            }
            R<CommunistUserInfo> userInfo = remoteCommunistService.getUserInfo(username, SecurityConstants.FROM_IN);
            userDetails = getCommunistUserDetails(userInfo);
            cache.put(username, userDetails);
            log.info("communist userDetails 放入缓存，username={} userDetails={}", username, JSONUtil.toJsonStr(userDetails));
        } else {
            if (cache != null && cache.get(username) != null) {
                return (PigUser) cache.get(username).get();
            }
            R<UserInfo> result = remoteUserService.info(username, SecurityConstants.FROM_IN);
            // TODO 新增查询
            userDetails = getUserDetails(result);
            cache.put(username, userDetails);
        }
        return userDetails;
    }

    /**
     * 构建user details
     * @param result
     * @return
     */
    private UserDetails getCommunistUserDetails(R<CommunistUserInfo> result) {
        if (result == null || result.getData() == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        CommunistUserInfo user = result.getData();
        Set<String> dbAuthsSet = new HashSet<>();
        dbAuthsSet.add(user.getOpenId());
        Collection<? extends GrantedAuthority> authorities
                = AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[0]));
        // 构造security用户
        return new PigUser(user.getType(), 1, user.getOpenId(), SecurityConstants.BCRYPT + user.getOpenId()
                ,user,true, true, true, true, authorities);

    }

    /**
     * 构建userdetails
     *
     * @param result 用户信息
     * @return
     */
    private UserDetails getUserDetails(R<UserInfo> result) {
        if (result == null || result.getData() == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        UserInfo info = result.getData();
        Set<String> dbAuthsSet = new HashSet<>();
        if (ArrayUtil.isNotEmpty(info.getRoles())) {
            // 获取角色
            Arrays.stream(info.getRoles()).forEach(role -> dbAuthsSet.add(SecurityConstants.ROLE + role));
            // 获取资源
            dbAuthsSet.addAll(Arrays.asList(info.getPermissions()));

        }
        Collection<? extends GrantedAuthority> authorities
                = AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[0]));
        SysUser user = info.getSysUser();

        // 构造security用户
        return new PigUser(user.getUserId(), user.getDeptId(), user.getUsername(), SecurityConstants.BCRYPT + user.getPassword(),
               new CommunistUserInfo(), StrUtil.equals(user.getLockFlag(), CommonConstants.STATUS_NORMAL), true, true, true, authorities);
    }
}
