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
import top.wecoding.core.security.interceptor.HeaderInterceptor;
import top.wecoding.core.security.props.IgnoreWhiteProperties;
import top.wecoding.core.security.props.SocialProperties;
import top.wecoding.core.security.provider.ClientDetailsService;
import top.wecoding.core.security.provider.client.JdbcClientDetailsService;

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
@EnableConfigurationProperties({IgnoreWhiteProperties.class, SocialProperties.class})
public class SecureConfiguration implements WebMvcConfigurer {

    public static final String[] defaultExcludePatterns = {"/token", "/*/v2/api-docs", "/profile/*"};
    private final JdbcTemplate jdbcTemplate;
    private final IgnoreWhiteProperties ignoreWhiteProperties;

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
        return new HeaderInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean(ClientDetailsService.class)
    public ClientDetailsService clientDetailsService() {
        return new JdbcClientDetailsService(jdbcTemplate);
    }

}
