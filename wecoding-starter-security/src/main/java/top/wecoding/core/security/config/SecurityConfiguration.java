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
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.wecoding.core.security.aspect.AuthAspect;
import top.wecoding.core.security.aspect.InnerAuthAspect;
import top.wecoding.core.security.interceptor.HeaderInterceptor;
import top.wecoding.core.security.props.SecurityProperties;
import top.wecoding.core.security.provider.ClientDetailsService;
import top.wecoding.core.security.provider.client.JdbcClientDetailsService;
import top.wecoding.core.security.registry.SecurityRegistry;
import top.wecoding.core.security.service.TokenService;

/**
 * 安全配置类
 *
 * @author liuyuhui
 * @date 2022
 * @qq 1515418211
 */
@Order
@AllArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({SecurityProperties.class})
public class SecurityConfiguration implements WebMvcConfigurer {

    private final JdbcTemplate jdbcTemplate;
    private final TokenService tokenService;
    private final SecurityRegistry securityRegistry;
    private final SecurityProperties securityProperties;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        if (securityRegistry.isEnabled()) {
            registry.addInterceptor(getHeaderInterceptor())
                    .excludePathPatterns(securityRegistry.getExcludePatterns())
                    .excludePathPatterns(securityRegistry.getDefaultExcludePatterns())
                    .excludePathPatterns(securityProperties.getWhites());
        }
    }

    @Bean
    @ConditionalOnMissingBean(ClientDetailsService.class)
    public ClientDetailsService clientDetailsService() {
        return new JdbcClientDetailsService(jdbcTemplate);
    }

    @Bean
    public InnerAuthAspect innerAuthAspect() {
        return new InnerAuthAspect();
    }

    @Bean
    public AuthAspect authAspect() {
        return new AuthAspect();
    }

    /**
     * 自定义请求头拦截器
     */
    public HeaderInterceptor getHeaderInterceptor() {
        return new HeaderInterceptor(tokenService);
    }

}
