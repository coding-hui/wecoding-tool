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

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import top.wecoding.core.cache.redis.service.RedisService;
import top.wecoding.core.constant.StrPool;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liuyuhui
 * @date 2022
 * @qq 1515418211
 */
@Slf4j
public abstract class BaseRedisCacheOperator<V> implements CacheOperator<String, V> {

    @Resource
    private RedisService redisService;

    @Override
    public void put(String key, V object) {
        redisService.set(getKeyPrefix() + key, object);
    }

    @Override
    public void put(String key, V object, long timeout) {
        redisService.set(getKeyPrefix() + key, object, timeout);
    }

    @Override
    public V get(String key) {
        return redisService.get(getKeyPrefix() + key);
    }

    @Override
    public void clear() {
        Set<String> keys = redisService.keys(getKeyPrefix() + StrPool.ASTERISK);
        if (keys != null) {
            redisService.del(keys);
        }
    }

    @Override
    public void remove(String key) {
        remove(Collections.singleton(key));
    }

    @Override
    public void remove(Collection<String> keys) {
        List<String> withPrefixKeys = keys.stream().map(i -> getKeyPrefix() + i).collect(Collectors.toList());
        redisService.del(withPrefixKeys);
    }

    @Override
    public int size() {
        Set<String> keys = redisService.keys(getKeyPrefix() + StrPool.ASTERISK);
        if (keys == null) {
            return 0;
        }
        return keys.size();
    }

    @Override
    public boolean isEmpty() {
        return this.size() <= 0;
    }

    @Override
    public boolean containsKey(String key) {
        return Boolean.TRUE.equals(redisService.hasKey(key));
    }

    @Override
    public Set<String> keySet() {
        Set<String> keys = redisService.keys(getKeyPrefix() + StrPool.ASTERISK);
        if (keys == null) {
            return CollectionUtil.newHashSet();
        }
        return keys.stream()
                .map(key -> StrUtil.removePrefix(key, getKeyPrefix()))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<V> values() {
        Set<String> keys = this.keySet();
        if (keys == null) {
            return CollectionUtil.newArrayList();
        }

        return redisService.mGet(keys);
    }

    @Override
    public Map<String, V> getAllKeyValues() {
        Collection<String> allKeys = this.keySet();
        HashMap<String, V> results = new HashMap<>();
        for (String key : allKeys) {
            results.put(key, this.get(key));
        }
        return results;
    }

}
