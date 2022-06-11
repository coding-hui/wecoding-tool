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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY StringIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.wecoding.core.cache.base;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * 规范缓存操作
 *
 * @author liuyuhui
 * @qq 1515418211
 */
public interface CacheOperator<T> {

    /**
     * 将对象加入到缓存
     *
     * @param key    键
     * @param object 缓存的对象
     */
    void set(String key, T object);

    /**
     * 将对象加入到缓存，使用指定失效时长
     *
     * @param key     键
     * @param object  缓存的对象
     * @param timeout 失效时长，单位毫秒
     */
    void set(String key, T object, long timeout);

    /**
     * 从缓存中获得对象
     *
     * @param key 键
     * @return 键对应的对象
     */
    T get(String key);

    /**
     * 从缓存中获得对象，不存在时，put 后返回
     *
     * @param key    键
     * @param loader 加载器
     * @return 键对应的对象
     */
    T get(String key, Supplier<T> loader);

    /**
     * 根据指定缓存 keys 获得对象
     *
     * @param keys 键
     * @return 不存在时，返回空集合
     */
    List<T> find(Collection<String> keys);

    /**
     * 从缓存中移除对象
     *
     * @param keys 键
     */
    void del(String... keys);

    /**
     * 删除缓存
     *
     * @param keys 键，多个
     */
    void del(Collection<String> keys);

    /**
     * 清空所有存储的数据，危险操作!!!
     */
    void flushDb();

    /**
     * 是否包含key
     *
     * @param key key
     * @return 是否存在
     */
    Boolean exists(String key);

    /**
     * 获取 key 中存放的 Long 值
     *
     * @param key 键
     * @return key中存储的的数字
     */
    Long getCounter(String key);

    /**
     * 为键 key 储存的数字值加上一。
     *
     * @param key 键
     * @return 递增结果
     */
    Long incr(String key);

    /**
     * 为键 key 储存的数字值加上 delta
     *
     * @param key   键
     * @param delta 增量值
     * @return 递增结果
     */
    Long incrBy(String key, long delta);

    /**
     * 为键 key 储存的数字值加上 delta
     *
     * @param key   键
     * @param delta 增量值
     * @return 递增结果
     */
    Double incrByFloat(String key, double delta);

    /**
     * 为键 key 储存的数字值减去一
     *
     * @param key 键
     * @return 递减结果
     */
    Long decr(String key);

    /**
     * 将 key 所储存的值减去减量 delta
     *
     * @param key   键
     * @param delta 增量值
     * @return 递减结果
     */
    Long decrBy(String key, long delta);

    /**
     * 缓存的前缀
     *
     * @return 缓存前缀
     */
    String getKeyPrefix();

}
