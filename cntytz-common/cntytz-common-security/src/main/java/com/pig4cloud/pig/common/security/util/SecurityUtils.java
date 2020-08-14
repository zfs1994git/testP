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

package com.pig4cloud.pig.common.security.util;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.pig4cloud.pig.common.core.constant.CacheConstants;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.SpringContextHolder;
import com.pig4cloud.pig.common.security.service.PigUser;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 安全工具类
 *
 * @author L.cm
 */
@Slf4j
@UtilityClass
public class SecurityUtils {

    private static final String SYS_TYPE_COMMUNIST = "communist";

    /**
     * 获取Authentication
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public CacheManager getCacheManager() {
        return SpringContextHolder.getBean("cacheManager");
    }

    /**
     * 获取用户
     */
    public PigUser getUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof PigUser) {
            return (PigUser) principal;
        }
        return null;
    }

    /**
     * 获取用户
     */
    public PigUser getUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return getUser(authentication);
    }

    /**
     * 获取用户角色信息
     *
     * @return 角色集合
     */
    public List<Integer> getRoles() {
        Authentication authentication = getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<Integer> roleIds = new ArrayList<>();
        authorities.stream()
                .filter(granted -> StrUtil.startWith(granted.getAuthority(), SecurityConstants.ROLE))
                .forEach(granted -> {
                    String id = StrUtil.removePrefix(granted.getAuthority(), SecurityConstants.ROLE);
                    roleIds.add(Integer.parseInt(id));
                });
        return roleIds;
    }

    /**
     * 获取缓存中的用户信息
     * @return
     */
    public PigUser getUserFromCache() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            log.error("用户工具类-获取security作用域为空！");
            return null;
        }
        Object principal = authentication.getPrincipal();
        PigUser pigUser = (PigUser) principal;
        log.info("用户工具类获取pigUser={}", JSONUtil.toJsonStr(pigUser));
        if (ObjectUtil.isNull(pigUser)) {
            log.error("用户工具类-获取security作用域用户信息为空！");
            return null;
        }
        String cacheKey = String.format("%s:%s", CacheConstants.USER_DETAILS, SYS_TYPE_COMMUNIST);
        Cache cache = getCacheManager().getCache(cacheKey);
        if (cache != null && cache.get(pigUser.getUsername()) != null) {
            PigUser user = (PigUser) cache.get(pigUser.getUsername()).get();
            log.info("用户工具类-缓存中获取security用户信息pigUser={}",JSONUtil.toJsonStr(user));
            return user;
        }
        log.error("用户工具类-获取redis缓存用户信息为空！");
        return null;
    }

    /**
     * 获取党员信息id
     * @return
     */
    public Long getCommunistUserId(){
        PigUser pigUser = getUserFromCache();
        if (pigUser == null){
            log.error("用户工具类-获取redis缓存用户信息为空！");
            return null;
        }
        if (pigUser.getCommunistUserInfo() == null){
            log.error("用户工具类-获取redis缓存党员用户信息为空！");
            return null;
        }
        return pigUser.getCommunistUserInfo().getId();
    }

}
