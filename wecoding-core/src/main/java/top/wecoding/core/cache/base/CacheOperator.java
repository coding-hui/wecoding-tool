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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 规范缓存操作
 *
 * @author liuyuhui
 * @qq 1515418211
 */
public interface CacheOperator<K, V> {

    /**
     * 将对象加入到缓存
     *
     * @param key    键
     * @param object 缓存的对象
     */
    void put(K key, V object);

    /**
     * 将对象加入到缓存，使用指定失效时长
     *
     * @param key     键
     * @param object  缓存的对象
     * @param timeout 失效时长，单位毫秒
     */
    void put(K key, V object, long timeout);

    /**
     * 从缓存中获得对象
     *
     * @param key 键
     * @return 键对应的对象
     */
    V get(K key);

    /**
     * 清空缓存
     */
    void clear();

    /**
     * 从缓存中移除对象
     *
     * @param key 键
     */
    void remove(K key);

    /**
     * 删除缓存
     *
     * @param keys 键，多个
     */
    void remove(Collection<K> keys);

    /**
     * 缓存的对象数量
     *
     * @return 缓存的对象数量
     */
    int size();

    /**
     * 缓存是否为空
     *
     * @return 缓存是否为空
     */
    boolean isEmpty();

    /**
     * 是否包含key
     *
     * @param key KEY
     * @return 是否包含key
     */
    boolean containsKey(K key);

    /**
     * 获得缓存的所有 key 列表
     *
     * @return all kes
     */
    Set<K> keySet();

    /**
     * 返回所有键
     *
     * @return 所有键
     */
    Collection<V> values();

    /**
     * 获取所有的 key,value
     *
     * @return 键值 map
     */
    Map<K, V> getAllKeyValues();

    /**
     * 缓存的前缀
     *
     * @return 缓存前缀
     */
    String getKeyPrefix();

}
