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

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author liuyuhui
 * @date 2022/6/11
 * @qq 1515418211
 */
@Slf4j
public abstract class BaseMemoryCacheOperator<T> implements CacheOperator<T> {

    /**
     * 最大数量
     */
    private static final long DEF_MAX_SIZE = 1_000;

    private final Cache<String, Cache<String, Object>> cacheMap = Caffeine.newBuilder()
            .maximumSize(DEF_MAX_SIZE)
            .build();

    @Override
    public void set(String key, T object) {
        if (object == null) {
            return;
        }

        Caffeine<Object, Object> cacheBuilder = Caffeine.newBuilder().maximumSize(DEF_MAX_SIZE);
        Cache<String, Object> cache = cacheBuilder.build();
        cache.put(key, object);
        cacheMap.put(key, cache);
    }

    @Override
    public void set(String key, T object, long timeout) {
        if (object == null) {
            return;
        }

        Caffeine<Object, Object> cacheBuilder = Caffeine.newBuilder()
                .expireAfterWrite(timeout, TimeUnit.SECONDS)
                .maximumSize(DEF_MAX_SIZE);
        Cache<String, Object> cache = cacheBuilder.build();
        cache.put(key, object);
        cacheMap.put(key, cache);
    }

    @Override
    public T get(String key) {
        Cache<String, Object> ifPresent = cacheMap.getIfPresent(key);
        if (ifPresent == null) {
            return null;
        }
        return (T) ifPresent.getIfPresent(key);
    }

    @Override
    public T get(String key, Supplier<T> loader) {
        Cache<String, Object> cache = cacheMap.get(key, (k) -> {
            Caffeine<Object, Object> builder = Caffeine.newBuilder()
                    .maximumSize(DEF_MAX_SIZE);
            Cache<String, Object> newCache = builder.build();
            newCache.get(k, (tk) -> loader.get());
            return newCache;
        });

        return (T) cache.getIfPresent(key);
    }

    @Override
    public List<T> find(Collection<String> keys) {
        return null;
    }

    @Override
    public Long del(String... keys) {
        for (String key : keys) {
            cacheMap.invalidate(key);
        }
        return (long) keys.length;
    }

    @Override
    public Long del(Collection<String> keys) {
        for (String key : keys) {
            cacheMap.invalidate(key);
        }
        return (long) keys.size();
    }

    @Override
    public void flushDb() {
        cacheMap.invalidateAll();
    }

    @Override
    public Boolean exists(String key) {
        return null;
    }

    @Override
    public Long getCounter(String key) {
        return null;
    }

    @Override
    public Long incr(String key) {
        return null;
    }

    @Override
    public Long incrBy(String key, long delta) {
        return null;
    }

    @Override
    public Double incrByFloat(String key, double delta) {
        return null;
    }

    @Override
    public Long decr(String key) {
        return null;
    }

    @Override
    public Long decrBy(String key, long delta) {
        return null;
    }

}
