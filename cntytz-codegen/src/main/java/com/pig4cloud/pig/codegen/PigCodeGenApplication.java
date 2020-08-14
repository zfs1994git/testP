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

package com.pig4cloud.pig.codegen;

import com.pig4cloud.pig.common.datasource.annotation.EnableDynamicDataSource;
import com.pig4cloud.pig.common.security.annotation.EnablePigFeignClients;
import com.pig4cloud.pig.common.security.annotation.EnablePigResourceServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author lengleng
 * @date 2020/03/11
 * 代码生成模块
 */
@Slf4j
@EnableDynamicDataSource
@EnablePigFeignClients
@SpringCloudApplication
@EnablePigResourceServer
public class PigCodeGenApplication {
    public static void main(String[] args) {
        SpringApplication.run(PigCodeGenApplication.class, args);
        log.info("------------cntytz-codegen started successfully-------------");
    }
}
