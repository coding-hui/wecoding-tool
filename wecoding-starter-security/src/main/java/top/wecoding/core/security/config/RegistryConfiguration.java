/*
 * Copyright (c) 2022. WeCoding (wecoding@yeah.net).
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/lgpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.wecoding.core.security.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import top.wecoding.core.security.registry.SecurityRegistry;

/**
 * @author liuyuhui
 * @date 2022/6/6
 * @qq 1515418211
 */
@Order
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(SecurityConfiguration.class)
public class RegistryConfiguration {

    /**
     * 服务可以自定义放行接口
     */
    @Bean
    @ConditionalOnMissingBean(SecurityRegistry.class)
    public SecurityRegistry securityRegistry() {
        return new SecurityRegistry();
    }

}
