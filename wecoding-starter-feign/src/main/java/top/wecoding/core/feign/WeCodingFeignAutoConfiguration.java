package top.wecoding.core.feign;

import com.alibaba.cloud.sentinel.feign.SentinelFeignAutoConfiguration;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.wecoding.core.feign.interceptor.FeignAddHeaderRequestInterceptor;

/**
 * @author liuyuhui
 * @date 2022/02/13
 * @qq 1515418211
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(SentinelFeignAutoConfiguration.class)
public class WeCodingFeignAutoConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignAddHeaderRequestInterceptor();
    }

}
