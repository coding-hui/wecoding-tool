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
package top.wecoding.core.cache;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
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
import top.wecoding.core.cache.factory.CreateRedisTemplateFactory;
import top.wecoding.core.cache.service.RedisService;
import top.wecoding.core.constant.StrPool;

import java.time.Duration;

/**
 * RedisTemplate 配置
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Slf4j
@EnableCaching
@AllArgsConstructor
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RedisConnectionFactory.class)
@AutoConfigureBefore(RedisAutoConfiguration.class)
@EnableConfigurationProperties(CacheProperties.class)
public class RedisAutoConfigure extends CachingConfigurerSupport {

    private final CacheProperties cacheProperties;

    @Bean("redisService")
    @ConditionalOnMissingBean
    public RedisService redisService(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        return new RedisService(redisTemplate, stringRedisTemplate);
    }

    @Bean("redisValueSerializer")
    public RedisSerializer<Object> redisValueSerializer() {
        return CreateRedisTemplateFactory.redisValueSerializer();
    }

    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        return CreateRedisTemplateFactory.createObject(connectionFactory);
    }

    @Bean("stringRedisTemplate")
    public RedisTemplate<String, String> stringRedisTemplate(RedisConnectionFactory connectionFactory) {
        return CreateRedisTemplateFactory.createString(connectionFactory);
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
        CacheProperties.Redis redis = cacheProperties.getRedis();
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisValueSerializer));

        if (redis.getTimeToLive() != null) {
            configuration.entryTtl(redis.getTimeToLive());
        }
        if (!redis.isCacheNullValues()) {
            configuration = configuration.disableCachingNullValues();
        }
        if (!redis.isUseKeyPrefix()) {
            configuration = configuration.disableKeyPrefix();
        }
        if (redis.getKeyPrefix() != null) {
            configuration = configuration.computePrefixWith(cacheName -> redis.getKeyPrefix().concat(StrPool.COLON).concat(cacheName).concat(StrPool.COLON));
        } else {
            configuration = configuration.computePrefixWith(cacheName -> cacheName.concat(StrPool.COLON));
        }

        return configuration;
    }

}
