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

package com.pig4cloud.pig.admin.api.feign;

import com.pig4cloud.pig.admin.api.dto.CommunistUserInfo;
import com.pig4cloud.pig.admin.api.feign.factory.RemoteCommunistServiceFallbackFactory;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author lixueliang
 * @date 2020/8/6
 */
@FeignClient(contextId = "remoteCommunistService", value = ServiceNameConstants.COMMUNIST_SERVICE, fallbackFactory = RemoteCommunistServiceFallbackFactory.class)
public interface RemoteCommunistService {
    /**
     * 查询用户
     * @param openid
     * @return
     */
    @GetMapping("/user/info/{openid}")
    R<CommunistUserInfo> getUserInfo(@PathVariable("openid") String openid,
                                     @RequestHeader(SecurityConstants.FROM) String from);

}
