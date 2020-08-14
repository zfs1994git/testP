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

package com.pig4cloud.pig.admin.api.feign.factory;

import com.pig4cloud.pig.admin.api.feign.RemoteCommunistService;
import com.pig4cloud.pig.admin.api.feign.RemoteUserService;
import com.pig4cloud.pig.admin.api.feign.fallback.RemoteCommunistServiceFallbackImpl;
import com.pig4cloud.pig.admin.api.feign.fallback.RemoteUserServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author lixueliang
 * @date 2020/8/6
 */
@Component
public class RemoteCommunistServiceFallbackFactory implements FallbackFactory<RemoteCommunistService> {

    @Override
    public RemoteCommunistService create(Throwable throwable) {
        RemoteCommunistServiceFallbackImpl remoteCommunistServiceFallback = new RemoteCommunistServiceFallbackImpl();
        remoteCommunistServiceFallback.setCause(throwable);
        return remoteCommunistServiceFallback;
    }
}