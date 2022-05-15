package top.wecoding.core.oss;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.wecoding.core.oss.props.FileStorageProperties;
import top.wecoding.core.oss.strategy.FileStorageContext;
import top.wecoding.core.oss.strategy.base.FileOperatorStrategy;
import top.wecoding.core.oss.strategy.local.LocalFileOperatorStrategy;

import java.util.Map;

/**
 * @author liuyuhui
 * @date 2022/04/04
 * @qq 1515418211
 */
@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(FileStorageProperties.class)
public class WeCodingOSSAutoConfigure {

    private final FileStorageProperties fileStorageProperties;

    @Bean("LOCAL")
    @ConditionalOnProperty(prefix = FileStorageProperties.PREFIX, name = "default-storage-type", havingValue = "LOCAL")
    public LocalFileOperatorStrategy localFileOperatorStrategy() {
        return new LocalFileOperatorStrategy(fileStorageProperties);
    }

    @Bean
    public FileStorageContext fileStorageContext(Map<String, FileOperatorStrategy> fileOperatorStrategyMap) {
        return new FileStorageContext(fileOperatorStrategyMap, fileStorageProperties);
    }

}
