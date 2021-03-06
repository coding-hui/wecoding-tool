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
package top.wecoding.core.cache.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import top.wecoding.core.cache.service.RedisService;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author liuyuhui
 * @date 2022
 * @qq 1515418211
 */
@Slf4j
public abstract class BaseRedisCacheOperator<T> implements CacheOperator<T> {

    @Resource
    private RedisService redisService;

    @Override
    public void set(String key, T object) {
        redisService.set(getKey(key), object);
    }

    @Override
    public void set(String key, T object, long timeout) {
        redisService.set(getKey(key), object, timeout);
    }

    @Override
    public T get(String key) {
        return redisService.get(getKey(key));
    }

    @Override
    public T get(String key, Supplier<T> loader) {
        return redisService.get(getKey(key), loader);
    }

    @Override
    public List<T> find(Collection<String> keys) {
        return redisService.mGet(getKeys(keys));
    }

    @Override
    public Long del(String... keys) {
        return redisService.del(getKeys(keys));
    }

    @Override
    public Long del(Collection<String> keys) {
        return redisService.del(getKeys(keys));
    }

    @Override
    public void flushDb() {
        redisService.getRedisTemplate().execute((RedisCallback<String>) connection -> {
            connection.flushDb();
            return "ok";
        });
    }

    @Override
    public Boolean exists(String key) {
        return redisService.hasKey(getKey(key));
    }

    @Override
    public Long getCounter(String key) {
        return redisService.getCounter(getKey(key));
    }

    @Override
    public Long incr(String key) {
        return redisService.incr(getKey(key));
    }

    @Override
    public Long incrBy(String key, long delta) {
        return redisService.incrBy(getKey(key), delta);
    }

    @Override
    public Double incrByFloat(String key, double delta) {
        return redisService.incrByFloat(getKey(key), delta);
    }

    @Override
    public Long decr(String key) {
        return redisService.decr(getKey(key));
    }

    @Override
    public Long decrBy(String key, long delta) {
        return redisService.decrBy(getKey(key), delta);
    }

}
