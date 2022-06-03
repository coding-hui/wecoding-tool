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
