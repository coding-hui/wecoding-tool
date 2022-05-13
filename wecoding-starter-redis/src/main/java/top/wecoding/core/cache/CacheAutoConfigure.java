package top.wecoding.core.cache;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.wecoding.core.cache.redis.RedisAutoConfigure;

/**
 * @author liuyuhui
 * @date 2022/02/13
 * @qq 1515418211
 */
@EnableCaching
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(RedisAutoConfigure.class)
public class CacheAutoConfigure {

    public static final Long SYSTEM_CONFIG_CACHE_TIMEOUT = 2 * 60 * 60 * 1000L;
    public static final Long VALIDATE_CACHE_TIMEOUT = 60 * 1000L;

    @Bean
    public SystemConfigCacheOperator systemConfigCache() {
        return new SystemConfigCacheOperator();
    }

    @Bean
    public ValidateCacheOperator validateCache() {
        return new ValidateCacheOperator();
    }

}
