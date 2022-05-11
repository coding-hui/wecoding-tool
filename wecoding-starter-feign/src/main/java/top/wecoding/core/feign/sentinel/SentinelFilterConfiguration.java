package top.wecoding.core.feign.sentinel;

import com.alibaba.cloud.sentinel.SentinelProperties;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.DefaultBlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.UrlCleaner;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.config.SentinelWebMvcConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @author liuyuhui
 * @date 2022/02/13
 * @qq 1515418211
 */
@RequiredArgsConstructor
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SentinelFilterConfiguration {

    @Bean
    public SentinelWebInterceptor sentinelWebInterceptor(SentinelWebMvcConfig sentinelWebMvcConfig) {
        return new SentinelWebInterceptor(sentinelWebMvcConfig);
    }

    @Bean
    public SentinelWebMvcConfig sentinelWebMvcConfig(SentinelProperties properties,
                                                     Optional<UrlCleaner> urlCleanerOptional,
                                                     Optional<BlockExceptionHandler> blockExceptionHandlerOptional,
                                                     Optional<RequestOriginParser> requestOriginParserOptional) {
        SentinelWebMvcConfig sentinelWebMvcConfig = new SentinelWebMvcConfig();
        sentinelWebMvcConfig.setHttpMethodSpecify(properties.getHttpMethodSpecify());
        sentinelWebMvcConfig.setWebContextUnify(properties.getWebContextUnify());

        if (blockExceptionHandlerOptional.isPresent()) {
            blockExceptionHandlerOptional.ifPresent(sentinelWebMvcConfig::setBlockExceptionHandler);
        } else {
            if (StringUtils.hasText(properties.getBlockPage())) {
                sentinelWebMvcConfig.setBlockExceptionHandler(
                        ((request, response, e) -> response.sendRedirect(properties.getBlockPage())));
            } else {
                sentinelWebMvcConfig.setBlockExceptionHandler(new DefaultBlockExceptionHandler());
            }
        }

        urlCleanerOptional.ifPresent(sentinelWebMvcConfig::setUrlCleaner);
        requestOriginParserOptional.ifPresent(sentinelWebMvcConfig::setOriginParser);
        return sentinelWebMvcConfig;
    }

}
