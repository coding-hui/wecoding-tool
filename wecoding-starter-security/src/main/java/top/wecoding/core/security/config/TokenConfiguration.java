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

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import top.wecoding.core.jwt.props.JwtProperties;
import top.wecoding.core.security.cache.LoginUserCache;
import top.wecoding.core.security.service.TokenService;

/**
 * Token 存储相关配置
 *
 * @author liuyuhui
 * @date 2022/6/6
 * @qq 1515418211
 */
@Order
@AllArgsConstructor
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(SecurityConfiguration.class)
@EnableConfigurationProperties({JwtProperties.class})
public class TokenConfiguration {

    private final JwtProperties jwtProperties;

    @Bean
    @ConditionalOnMissingBean(TokenService.class)
    public TokenService tokenService(LoginUserCache loginUserCache) {
        return new TokenService(jwtProperties, loginUserCache);
    }

    @Bean
    @ConditionalOnMissingBean(LoginUserCache.class)
    public LoginUserCache loginUserCache() {
        return new LoginUserCache();
    }

}
