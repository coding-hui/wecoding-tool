package top.wecoding.core.jwt;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import top.wecoding.core.jwt.props.JwtProperties;

/**
 * @author liuyuhui
 * @date 2022/5/13
 * @qq 1515418211
 */
@AllArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(value = {JwtProperties.class})
public class JwtConfiguration {
}
