package top.wecoding.core.cache.base;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 基于内存的缓存封装
 *
 * @author liuyuhui
 * @date 2021/07/30
 * @qq 1515418211
 */
public abstract class BaseMemoryCacheOperator<V> implements CacheOperator<String, V> {

    /**
     * 最大数量
     */
    private static final long DEF_MAX_SIZE = 1_000;

    private final Cache<String, Cache<String, Object>> cacheMap = Caffeine.newBuilder()
            .maximumSize(DEF_MAX_SIZE)
            .build();

    @Override
    public void put(String key, V object) {

    }

    @Override
    public V get(String key) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public void remove(String key) {

    }

    @Override
    public void remove(Collection<String> keys) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(String key) {
        return false;
    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Map<String, V> getAllKeyValues() {
        return null;
    }

    @Override
    public void put(String key, V object, long timeout) {
        if (object == null) {
            return;
        }
        Caffeine<Object, Object> builder = Caffeine.newBuilder()
                .maximumSize(DEF_MAX_SIZE);
        Cache<String, Object> cache = builder.build();
        cache.put(key, object);
        cacheMap.put(key, cache);
    }

}
