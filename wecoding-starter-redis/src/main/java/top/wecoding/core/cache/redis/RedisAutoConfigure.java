package top.wecoding.core.cache.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import top.wecoding.core.cache.redis.factory.CreateRedisTemplateFactory;
import top.wecoding.core.cache.redis.service.RedisService;
import top.wecoding.core.constant.StrPool;

import java.time.Duration;

/**
 * RedisTemplate 配置
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RedisConnectionFactory.class)
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisAutoConfigure extends CachingConfigurerSupport {

    @Bean
    @ConditionalOnMissingBean
    public RedisService redisService(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        return new RedisService(redisTemplate, stringRedisTemplate);
    }

    @Bean
    public RedisSerializer<Object> redisValueSerializer() {
        return CreateRedisTemplateFactory.redisValueSerializer();
    }

    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        return CreateRedisTemplateFactory.createObject(connectionFactory);
    }

    @Bean
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory,
                                     RedisSerializer<Object> redisValueSerializer) {
        log.info(" >>> 启动 Redis 缓存.");

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(createRedisCacheConfiguration(redisValueSerializer))
                .transactionAware()
                .build();
    }

    private RedisCacheConfiguration createRedisCacheConfiguration(RedisSerializer<Object> redisValueSerializer) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1))
                .computePrefixWith(prefix -> prefix + StrPool.COLON)
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisValueSerializer));
    }

}
