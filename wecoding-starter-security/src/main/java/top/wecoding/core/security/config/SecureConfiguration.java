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
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.wecoding.core.cache.redis.service.RedisService;
import top.wecoding.core.jwt.props.JwtProperties;
import top.wecoding.core.security.cache.LoginUserCache;
import top.wecoding.core.security.interceptor.HeaderInterceptor;
import top.wecoding.core.security.props.IgnoreWhiteProperties;
import top.wecoding.core.security.props.SocialProperties;
import top.wecoding.core.security.provider.ClientDetailsService;
import top.wecoding.core.security.provider.client.JdbcClientDetailsService;
import top.wecoding.core.security.util.TokenService;

/**
 * 安全配置类
 *
 * @author liuyuhui
 * @date 2022
 * @qq 1515418211
 */
@Order
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({
        IgnoreWhiteProperties.class,
        SocialProperties.class,
        JwtProperties.class
})
public class SecureConfiguration implements WebMvcConfigurer {

    public static final String[] defaultExcludePatterns = {"/token", "/*/v2/api-docs", "/profile/*"};
    private final JdbcTemplate jdbcTemplate;
    private final IgnoreWhiteProperties ignoreWhiteProperties;
    private final JwtProperties jwtProperties;
    private final RedisService redisService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getHeaderInterceptor())
                .excludePathPatterns(ignoreWhiteProperties.getWhites())
                .excludePathPatterns(defaultExcludePatterns);
    }

    /**
     * 自定义请求头拦截器
     */
    public HeaderInterceptor getHeaderInterceptor() {
        return new HeaderInterceptor(tokenUtil());
    }

    @Bean
    @ConditionalOnMissingBean(ClientDetailsService.class)
    public ClientDetailsService clientDetailsService() {
        return new JdbcClientDetailsService(jdbcTemplate);
    }

    @Bean
    public TokenService tokenUtil() {
        return new TokenService(jwtProperties, loginUserCache());
    }

    @Bean
    public LoginUserCache loginUserCache() {
        return new LoginUserCache(redisService);
    }

}
