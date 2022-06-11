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
package top.wecoding.core.cache.service;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import top.wecoding.core.cache.base.CacheKey;
import top.wecoding.core.exception.Assert;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * spring redis 工具类
 *
 * @author liuyuhui
 **/
@Slf4j
@Getter
@SuppressWarnings({"unused", "SpellCheckingInspection", "unchecked"})
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final ValueOperations<String, Object> valueOps;
    private final HashOperations<String, Object, Object> hashOps;
    private final ListOperations<String, Object> listOps;
    private final SetOperations<String, Object> setOps;
    private final ZSetOperations<String, Object> zSetOps;

    public RedisService(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        Assert.notNull(redisTemplate, " >>> redisTemplate 不能为空.");
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
        this.valueOps = redisTemplate.opsForValue();
        this.hashOps = redisTemplate.opsForHash();
        this.listOps = redisTemplate.opsForList();
        this.setOps = redisTemplate.opsForSet();
        this.zSetOps = redisTemplate.opsForZSet();
    }

    //============================Common Start=============================//

    /**
     * 设置有效时间
     *
     * @param key     键
     * @param seconds 超时时间/秒
     * @return 操作结果
     */
    public Boolean expire(final String key, final long seconds) {
        return expire(key, seconds, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key      键
     * @param duration 超时时间
     * @return 操作结果
     */
    public Boolean expire(final String key, final Duration duration) {
        return expire(key, duration.getSeconds());
    }

    /**
     * 设置有效时间
     *
     * @param key     键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return 操作结果
     */
    public Boolean expire(final String key, final long timeout, final TimeUnit unit) {
        try {
            if (timeout > 0) {
                redisTemplate.expire(key, timeout, unit);
            }
            return true;
        } catch (Exception e) {
            log.error(" >>> 设置 Redis 有效时间失败: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 为 key 设置生存时间
     *
     * @param key           键
     * @param unixTimestamp 过期时间戳
     * @return 操作结果
     */
    public Boolean expireAt(final String key, final long unixTimestamp) {
        return expireAt(key, new Date(unixTimestamp));
    }

    /**
     * 为 key 设置生存时间
     *
     * @param key  键
     * @param date 过期时间
     * @return 操作结果
     */
    public Boolean expireAt(final String key, final Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 为 key 设置生存时间
     *
     * @param key          键
     * @param milliseconds 过期时间戳
     * @return 操作结果
     */
    public Boolean pExpire(final String key, final long milliseconds) {
        return redisTemplate.expire(key, milliseconds, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取有效时间.
     *
     * @param key 键
     * @return 有效时间
     */
    public Long getExpire(final String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 移除给定 key 的生存时间.
     *
     * @param key 键
     * @return 操作结果
     */
    public Boolean persist(final String key) {
        return redisTemplate.persist(key);
    }

    /**
     * 返回 key 所储存的值的类型.
     *
     * @param key 键
     * @return none (key不存在)、string (字符串)、list (列表)、set (集合)、zset (有序集)、hash (哈希表) 、stream （流）
     */
    public String type(final String key) {
        DataType type = redisTemplate.type(key);
        return type == null ? DataType.NONE.code() : type.code();
    }

    /**
     * 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)。
     *
     * @param key 键
     * @return key 不存在返回 -2,存在但没有生存时间时，返回 -1。否则，以秒为单位，返回 key 的剩余生存时间。
     */
    public Long ttl(final String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 以毫秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)。
     *
     * @param key 键
     * @return key 不存在返回 -2,存在但没有生存时间时，返回 -1。否则，以秒为单位，返回 key 的剩余生存时间。
     */
    public Long pTtl(String key) {
        return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
    }

    /**
     * 删除缓存
     *
     * @param keys 缓存 keys
     * @return 操作结果
     */
    public Long del(final CacheKey... keys) {
        return del(Arrays.stream(keys).map(CacheKey::getKey).collect(Collectors.toList()));
    }

    /**
     * 删除缓存
     *
     * @param keys 缓存 keys
     * @return 操作结果
     */
    public Long del(String... keys) {
        return del(Arrays.stream(keys).collect(Collectors.toList()));
    }

    /**
     * 删除缓存
     *
     * @param keys 缓存 keys
     * @return 操作结果
     */
    public Long del(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 异步删除一个 key 或多个key.
     *
     * @param keys 缓存 keys
     * @return 操作结果
     */
    public Long unlink(final CacheKey... keys) {
        return unlink(Arrays.stream(keys).map(CacheKey::getKey).collect(Collectors.toList()));
    }

    /**
     * 异步删除一个 key 或多个key.
     *
     * @param keys 缓存 keys
     * @return 操作结果
     */
    public Long unlink(final String... keys) {
        return unlink(Arrays.stream(keys).collect(Collectors.toList()));
    }

    /**
     * 异步删除一个 key 或多个key.
     *
     * @param keys 缓存 keys
     * @return 操作结果
     */
    public Long unlink(final List<String> keys) {
        return redisTemplate.unlink(keys);
    }

    /**
     * 查找所有符合给定模式 pattern 的 key 。
     * <p>
     * 例子：
     * KEYS * 匹配数据库中所有 key 。
     * KEYS h?llo 匹配 hello ， hallo 和 hxllo 等。
     * KEYS h*llo 匹配 hllo 和 heeeeello 等。
     * KEYS h[ae]llo 匹配 hello 和 hallo ，但不匹配 hillo 。
     * <p>
     * 特殊符号用 \ 隔开
     *
     * @param pattern 表达式
     * @return 符合给定模式的 key 列表
     */
    public List<String> scan(final String pattern) {
        List<String> keyList = new ArrayList<>();
        scan(pattern, item -> {
            Object key = redisTemplate.getKeySerializer().deserialize(item);
            if (ObjectUtil.isNotEmpty(key)) {
                keyList.add(String.valueOf(key));
            }
        });
        return keyList;
    }

    /**
     * scan
     *
     * @param pattern  表达式
     * @param consumer 对每个 key 进行的操作
     */
    private void scan(final String pattern, Consumer<byte[]> consumer) {
        redisTemplate.execute((RedisConnection connection) -> {
            try (Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(pattern).build())) {
                cursor.forEachRemaining(consumer);
                return null;
            }
        });
    }

    /**
     * 查找所有符合给定模式 pattern 的 key 。
     * <p>
     * 例：
     * KEYS * 匹配数据库中所有 key 。
     * KEYS h?llo 匹配 hello ， hallo 和 hxllo 等。
     * KEYS h*llo 匹配 hllo 和 heeeeello 等。
     * KEYS h[ae]llo 匹配 hello 和 hallo ，但不匹配 hillo 。
     * <p>
     * 特殊符号用 \ 隔开
     *
     * @param pattern 表达式
     * @return 符合给定模式的 key 列表
     */
    public Set<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 从当前数据库中随机返回(不删除)一个 key 。
     *
     * @return 当数据库不为空时，返回一个 key。当数据库为空时，返回 nil 。
     */
    public String randomKey() {
        return redisTemplate.randomKey();
    }

    /**
     * 将 key 改名为 newkey 。
     * 当 key 和 newkey 相同，或者 key 不存在时，返回一个错误。
     * 当 newkey 已经存在时， RENAME 命令将覆盖旧值。
     *
     * @param oldKey 旧键
     * @param newKey 新键
     */
    public void rename(final String oldKey, final String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 当且仅当 newkey 不存在时，将 key 改名为 newkey 。
     *
     * @param oldKey 旧键
     * @param newKey 新键
     * @return 操作结果
     */
    public Boolean renameNx(final String oldKey, final String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 将当前数据库的 key 移动到给定的数据库 db 当中。
     *
     * @param key     键
     * @param dbIndex 数据库索引
     * @return 操作结果
     */
    public Boolean move(final String key, final int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    //============================Common End=============================//

    //============================String=============================//

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public <T> T get(String key) {
        return key == null ? null : (T) valueOps.get(key);
    }

    /**
     * 普通缓存获取，cache 为 null 时使用给定的加载器，设置缓存
     *
     * @param key    键
     * @param loader cache supplier
     * @return 缓存结果
     */
    public <T> T get(String key, Supplier<T> loader) {
        T value = get(key);
        if (value != null) {
            return value;
        }
        value = loader.get();
        if (value == null) {
            return null;
        }
        this.set(key, value);
        return value;
    }

    /**
     * 返回所有(一个或多个)给定 key 的值, 值按请求的键的顺序返回。
     * 如果给定的 key 里面，有某个 key 不存在，那么这个 key 返回特殊值 nil
     *
     * @param keys 缓存 keys
     * @return 按给定key顺序排列的值列表
     */
    public <T> List<T> mGet(String... keys) {
        return mGet(Arrays.asList(keys));
    }

    /**
     * 返回所有(一个或多个)给定 key 的值, 值按请求的键的顺序返回。
     * 如果给定的 key 里面，有某个 key 不存在，那么这个 key 返回特殊值 nil
     *
     * @param keys 缓存 keys
     * @return 按给定key顺序排列的值列表
     */
    public <T> List<T> mGet(final Collection<String> keys) {
        List<T> list = (List<T>) valueOps.multiGet(keys);
        return list == null ? Collections.emptyList() : list;
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return 操作结果
     */
    public Boolean set(final String key, final Object value) {
        try {
            valueOps.set(key, value);
            return true;
        } catch (Exception e) {
            log.error(" >>> 普通缓存放入 Redis 失败：{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return 操作结果
     */
    public Boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                valueOps.set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error(" >>> 普通缓存放入 Redis 失败：{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key      键
     * @param value    值
     * @param time     时间
     * @param timeUnit 时间单位
     * @return 操作结果
     */
    public Boolean set(String key, Object value, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                valueOps.set(key, value, time, timeUnit);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error(" >>> 普通缓存放入 Redis 失败：{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 存放 key value 对到 redis，并将 key 的生存时间设为 seconds (以秒为单位)。
     * 如果 key 已经存在， SETEX 命令将覆写旧值。
     *
     * @param key     键
     * @param value   值
     * @param timeout 超时时间
     */
    public void setEx(String key, Object value, Duration timeout) {
        valueOps.set(key, value, timeout);
    }

    /**
     * 存放 key value 对到 redis，并将 key 的生存时间设为 seconds (以秒为单位)。
     * 如果 key 已经存在， SETEX 命令将覆写旧值。
     *
     * @param key     键
     * @param value   值
     * @param seconds 超时时间
     */
    public void setEx(String key, Object value, Long seconds) {
        valueOps.set(key, value, seconds, TimeUnit.SECONDS);
    }

    /**
     * 返回键 key 储存的字符串值的指定部分， 字符串的截取范围由 start 和 end 两个偏移量决定 (包括 start 和 end 在内)。
     * 负数偏移量表示从字符串的末尾开始计数， -1 表示最后一个字符， -2 表示倒数第二个字符， 以此类推。
     * GETRANGE 通过保证子字符串的值域(range)不超过实际字符串的值域来处理超出范围的值域请求。
     *
     * @param key   键
     * @param start 开始偏移量
     * @param end   结束偏移量
     * @return GETRANGE 命令会返回字符串值的指定部分。
     * @see <a href="https://redis.io/commands/getrange">Redis Documentation: GETRANGE</a>
     */
    public String getRange(final String key, long start, long end) {
        return valueOps.get(key, start, end);
    }

    /**
     * 同时为一个或多个键设置值。
     * 如果某个给定键已经存在，那么 MSET 将使用新值去覆盖旧值，
     * 如果这不是你所希望的效果，请考虑使用 MSETNX 命令，这个命令只会在所有给定键都不存在的情况下进行设置。
     * MSET 是一个原子性(atomic)操作，所有给定键都会在同一时间内被设置，不会出现某些键被设置了但是另一些键没有被设置的情况。   *
     *
     * @param map k-v
     */
    public void mSet(Map<String, Object> map) {
        valueOps.multiSet(map);
    }

    /**
     * 当且仅当所有给定键都不存在时， 为所有给定键设置值。
     * 即使只有一个给定键已经存在， MSETNX 命令也会拒绝执行对所有键的设置操作。
     * MSETNX 是一个原子性(atomic)操作，所有给定键要么就全部都被设置，要么就全部都不设置，不可能出现第三种状态。
     *
     * @param map k-v
     */
    public void mSetNx(final Map<String, Object> map) {
        valueOps.multiSetIfAbsent(map);
    }

    /**
     * 返回键 key 储存的字符串值的长度
     *
     * @param key 键
     * @return 字符串值的长度。当键 key 不存在时，返回 0
     */
    public Long strLen(final String key) {
        return valueOps.size(key);
    }

    /**
     * 将 key 中储存的数字值增一。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
     *
     * @param key 键
     * @return long
     */
    public Long incr(String key) {
        return valueOps.increment(key);
    }

    /**
     * 将 key 所储存的值加上增量 increment 。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return long
     */
    public Long incrBy(String key, long delta) {
        Assert.isTrue(delta < 0, "递增因子必须大于0");
        return valueOps.increment(key, delta);
    }

    /**
     * 将 key 所储存的值加上增量 delta 。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return long
     */
    public Double incrByFloat(String key, double delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 将 key 中储存的数字值减一。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR 操作。
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
     * 关于递增(increment) / 递减(decrement)操作的更多信息，请参见 INCR 命令。
     *
     * @param key 键
     * @return long
     */
    public Long decr(String key) {
        return valueOps.decrement(key);
    }

    /**
     * 将 key 所储存的值减去减量 decrement 。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECRBY 操作。
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return long
     */
    public Long decrBy(String key, long delta) {
        Assert.isTrue(delta < 0, "递减因子必须大于0");
        return valueOps.increment(key, -delta);
    }

    /**
     * 获取记数器的值
     *
     * @param key 键
     * @return long
     */
    public Long getCounter(String key) {
        return Long.valueOf(String.valueOf(valueOps.get(key)));
    }

    // ---------------------------- hash start ----------------------------//

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public <T> T hGet(final String key, final String item) {
        return (T) hashOps.get(key, item);
    }

    /**
     * 返回哈希表 key 中，一个或多个给定域的值。
     * 如果给定的域不存在于哈希表，那么返回一个 nil 值。
     * 因为不存在的 key 被当作一个空哈希表来处理，所以对一个不存在的 key 进行 HMGET 操作将返回一个只带有 nil 值的表。
     *
     * @param key      键
     * @param hashKeys 域值
     * @return 值
     */
    public <T> List<T> hmGet(String key, Object... hashKeys) {
        return (List<T>) hashOps.multiGet(key, Arrays.asList(hashKeys));
    }

    /**
     * 返回哈希表 key 中，一个或多个给定域的值。
     * 如果给定的域不存在于哈希表，那么返回一个 nil 值。
     * 因为不存在的 key 被当作一个空哈希表来处理，所以对一个不存在的 key 进行 HMGET 操作将返回一个只带有 nil 值的表。
     *
     * @param key      键
     * @param hashKeys 域值
     * @return 值
     */
    public <T> List<T> hmGet(String key, Collection<Object> hashKeys) {
        return (List<T>) hashOps.multiGet(key, hashKeys);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmGetMap(String key) {
        return hashOps.entries(key);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public Boolean hSet(String key, String item, Object value) {
        try {
            hashOps.put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error(" >>> 放入 Redis HashSet 失败，{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public Boolean hSet(String key, String item, Object value, long time) {
        try {
            hashOps.put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error(" >>> 放入 Redis HashSet 失败，{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public Boolean hmSet(String key, Map<String, Object> map) {
        try {
            hashOps.putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error(" >>> 放入 Redis HashSet 失败，{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return 操作结果
     */
    public Boolean hmSet(String key, Map<String, Object> map, long time) {
        try {
            hashOps.putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error(" >>> 放入 Redis HashSet 失败，{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hDel(String key, Object... item) {
        hashOps.delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public Boolean hHasKey(String key, String item) {
        return hashOps.hasKey(key, item);
    }

    /**
     * 返回哈希表 key 中域的数量。
     *
     * @param key 键
     * @return 哈希表中域的数量。
     */
    public Long hLen(final String key) {
        return hashOps.size(key);
    }

    /**
     * 返回哈希表 key 中，所有的域和值。
     * 在返回值里，紧跟每个域名(field name)之后是域的值(value)，所以返回值的长度是哈希表大小的两倍。
     *
     * @param key 键
     * @return 哈希表中域和值
     */
    public <K, V> Map<K, V> hGetAll(final String key) {
        return (Map<K, V>) hashOps.entries(key);
    }

    /**
     * 返回哈希表 key 中所有域的值。
     *
     * @param key 键
     * @return 一个包含哈希表中所有值的表。
     */
    public <HV> List<HV> hVals(final String key) {
        return (List<HV>) hashOps.values(key);
    }

    /**
     * 为哈希表 key 中的域 field 的值加上增量 increment 。
     * 增量也可以为负数，相当于对给定域进行减法操作。
     * 如果 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。
     * 如果域 field 不存在，那么在执行命令前，域的值被初始化为 0 。
     * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误。
     * 本操作的值被限制在 64 位(bit)有符号数字表示之内。
     *
     * @param key   键
     * @param field 项
     * @param delta 要增加几(大于0)
     * @return Long
     */
    public Long hIncrBy(String key, String field, long delta) {
        return hashOps.increment(key, field, delta);
    }

    /**
     * 为哈希表 key 中的域 field 加上浮点数增量 increment 。
     * 如果哈希表中没有域 field ，那么 HINCRBYFLOAT 会先将域 field 的值设为 0 ，然后再执行加法操作。
     * 如果键 key 不存在，那么 HINCRBYFLOAT 会先创建一个哈希表，再创建域 field ，最后再执行加法操作。
     * 当以下任意一个条件发生时，返回一个错误：
     * 1:域 field 的值不是字符串类型(因为 redis 中的数字和浮点数都以字符串的形式保存，所以它们都属于字符串类型）
     * 2:域 field 当前的值或给定的增量 increment 不能解释(parse)为双精度浮点数(double precision floating point number)
     *
     * @param key   键
     * @param field 项
     * @param delta 要增加几(大于0)
     * @return double
     */
    public Double hIncrByFloat(String key, Object field, double delta) {
        return hashOps.increment(key, field, delta);
    }

    //============================Hast End=============================//

    //============================Set Start=============================//

    /**
     * 判断 member 元素是否集合 key 的成员。
     *
     * @param key    键
     * @param member 元素
     * @return 如果 member 元素是集合的成员，返回 1。如果 member 元素不是集合的成员，或 key 不存在，返回 0。
     */
    public Boolean sIsMember(final String key, Object member) {
        return setOps.isMember(key, member);
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long sAdd(final String key, Object... values) {
        try {
            return setOps.add(key, values);
        } catch (Exception e) {
            log.error(" >>> Redis 将set数据放入缓存失败，{}", e.getMessage(), e);
            return 0L;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long sAdd(final String key, final long time, final Object... values) {
        try {
            Long count = setOps.add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            log.error(" >>> Redis 将set数据放入缓存失败，{}", e.getMessage(), e);
            return 0L;
        }
    }

    /**
     * 移除并返回集合中的一个随机元素。
     * 如果只想获取一个随机元素，但不想该元素从集合中被移除的话，可以使用 SRANDMEMBER 命令。
     *
     * @param key 键
     * @return 被移除的随机元素。当 key 不存在或 key 是空集时，返回 nil 。
     */
    public <T> T sPop(final String key) {
        return (T) setOps.pop(key);
    }

    /**
     * 返回集合中的一个随机元素。
     *
     * @param key 键
     * @return 只提供 key 参数时，返回一个元素；如果集合为空，返回 nil
     */
    public <T> T sRandMember(final String key) {
        return (T) setOps.randomMember(key);
    }

    /**
     * 返回集合中的count个随机元素。
     * <p>
     * 如果 count 为正数，且小于集合基数，那么命令返回一个包含 count 个元素的数组，数组中的元素各不相同。如果 count 大于等于集合基数，那么返回整个集合。
     * 如果 count 为负数，那么命令返回一个数组，数组中的元素可能会重复出现多次，而数组的长度为 count 的绝对值。
     *
     * @param key   键
     * @param count 数量
     * @return 只提供 key 参数时，返回一个元素；如果集合为空，返回 nil
     */
    public <V> Set<V> sRandMember(final String key, long count) {
        return (Set<V>) setOps.distinctRandomMembers(key, count);
    }

    /**
     * 返回集合中的count个随机元素。
     * <p>
     * 如果 count 为正数，且小于集合基数，那么命令返回一个包含 count 个元素的数组，数组中的元素各不相同。如果 count 大于等于集合基数，那么返回整个集合。
     * 如果 count 为负数，那么命令返回一个数组，数组中的元素可能会重复出现多次，而数组的长度为 count 的绝对值。
     *
     * @param key   键
     * @param count 数量
     * @return 只提供 key 参数时，返回一个元素；如果集合为空，返回 nil
     */
    public <V> List<V> sRandMembers(final String key, long count) {
        return (List<V>) setOps.randomMembers(key, count);
    }

    /**
     * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。
     * 当 key 不是集合类型，返回一个错误。
     *
     * @param key     键
     * @param members 元素
     * @return 被成功移除的元素的数量，不包括被忽略的元素
     */
    public Long sRem(final String key, final Object... members) {
        return setOps.remove(key, members);
    }

    /**
     * 将 member 元素从 source 集合移动到 destination 集合。
     * SMOVE 是原子性操作。
     * 如果 source 集合不存在或不包含指定的 member 元素，则 SMOVE 命令不执行任何操作，仅返回 0 。否则， member 元素从 source 集合中被移除，并添加到 destination 集合中去。
     * 当 destination 集合已经包含 member 元素时， SMOVE 命令只是简单地将 source 集合中的 member 元素删除。
     * 当 source 或 destination 不是集合类型时，返回一个错误。
     *
     * @param sourceKey      源key
     * @param destinationKey 目的key
     * @param value          值
     * @return 是否成功
     */
    public <V> Boolean sMove(final String sourceKey, final String destinationKey, final V value) {
        return setOps.move(sourceKey, value, destinationKey);
    }

    /**
     * 返回集合 key 的基数(集合中元素的数量)。
     *
     * @param key 键
     * @return 集合的基数。当 key 不存在时，返回 0。
     */
    public Long sCard(final String key) {
        return setOps.size(key);
    }

    /**
     * 返回集合 key 中的所有成员。
     * 不存在的 key 被视为空集合。
     *
     * @param key 键
     * @return 集合中的所有成员。
     */
    public <V> Set<V> sMembers(final String key) {
        return (Set<V>) setOps.members(key);
    }

    /**
     * 返回一个集合的全部成员，该集合是所有给定集合的交集。
     * 不存在的 key 被视为空集。
     * 当给定集合当中有一个空集时，结果也为空集(根据集合运算定律)。
     *
     * @param key      键
     * @param otherKey 键
     * @return 交集成员的列表。
     */
    public <V> Set<V> sInter(final String key, final String otherKey) {
        return (Set<V>) setOps.intersect(key, otherKey);
    }

    /**
     * 返回一个集合的全部成员，该集合是所有给定集合的交集。
     * 不存在的 key 被视为空集。
     * 当给定集合当中有一个空集时，结果也为空集(根据集合运算定律)。
     *
     * @param key       键
     * @param otherKeys 键
     * @return 交集成员的列表。
     */
    public Set<Object> sInter(final String key, final Collection<String> otherKeys) {
        return setOps.intersect(key, otherKeys);
    }

    /**
     * 返回一个集合的全部成员，该集合是所有给定集合的交集。
     * <p>
     * 不存在的 key 被视为空集。
     * <p>
     * 当给定集合当中有一个空集时，结果也为空集(根据集合运算定律)。
     *
     * @param otherKeys 键
     * @return 交集成员的列表。
     */
    public <V> Set<V> sInter(final Collection<String> otherKeys) {
        return (Set<V>) setOps.intersect(otherKeys);
    }


    /**
     * 这个命令类似于 SINTER key [key …] 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集。
     * 如果 destination 集合已经存在，则将其覆盖。
     * destination 可以是 key 本身。
     *
     * @param key      键
     * @param otherKey 键
     * @param destKey  键
     * @return 结果集中的成员数量。
     */
    public Long sInterStore(final String key, final String otherKey, final String destKey) {
        return setOps.intersectAndStore(key, otherKey, destKey);
    }

    /**
     * 这个命令类似于 SINTER key [key …] 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集。
     * 如果 destination 集合已经存在，则将其覆盖。
     * destination 可以是 key 本身。
     *
     * @param key       键
     * @param otherKeys 键
     * @param destKey   键
     * @return 结果集中的成员数量。
     */
    public Long sInterStore(final String key, final Collection<String> otherKeys, final String destKey) {
        return setOps.intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * 这个命令类似于 SINTER key [key …] 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集。
     * 如果 destination 集合已经存在，则将其覆盖。
     * destination 可以是 key 本身。
     *
     * @param otherKeys 键
     * @param destKey   键
     * @return 结果集中的成员数量。
     */
    public Long sInterStore(final Collection<String> otherKeys, final String destKey) {
        return setOps.intersectAndStore(otherKeys, destKey);
    }

    /**
     * 返回多个集合的并集，多个集合由 keys 指定
     * 不存在的 key 被视为空集。
     *
     * @param key      键
     * @param otherKey 键
     * @return when used in pipeline / transaction.
     */
    public <V> Set<V> sUnion(final String key, final String otherKey) {
        return (Set<V>) setOps.union(key, otherKey);
    }

    /**
     * 返回多个集合的并集，多个集合由 keys 指定
     * 不存在的 key 被视为空集。
     *
     * @param key       键
     * @param otherKeys 键
     * @return when used in pipeline / transaction.
     */
    public <V> Set<V> sUnion(final String key, final Collection<String> otherKeys) {
        return (Set<V>) setOps.union(key, otherKeys);
    }

    /**
     * 返回多个集合的并集，多个集合由 keys 指定
     * 不存在的 key 被视为空集。
     *
     * @param otherKeys 键
     * @return when used in pipeline / transaction.
     */
    public <V> Set<V> sUnion(final Collection<String> otherKeys) {
        return (Set<V>) setOps.union(otherKeys);
    }

    /**
     * 这个命令类似于 SUNION key [key …] 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集。
     * 如果 destination 已经存在，则将其覆盖。
     * destination 可以是 key 本身。
     *
     * @param key      键
     * @param otherKey 键
     * @param distKey  键
     * @return when used in pipeline / transaction.
     */
    public Long sUnionStore(final String key, final String otherKey, final String distKey) {
        return setOps.unionAndStore(key, otherKey, distKey);
    }

    /**
     * 这个命令类似于 SUNION key [key …] 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集。
     * 如果 destination 已经存在，则将其覆盖。
     * destination 可以是 key 本身。
     *
     * @param otherKeys 键
     * @param distKey   键
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sunionstore">Redis Documentation: SUNIONSTORE</a>
     */
    public Long sUnionStore(final Collection<String> otherKeys, final String distKey) {
        return setOps.unionAndStore(otherKeys, distKey);
    }

    /**
     * 返回一个集合的全部成员，该集合是所有给定集合之间的差集。
     * 不存在的 key 被视为空集。
     *
     * @param key      键
     * @param otherKey 键
     * @return 一个包含差集成员的列表。
     * @see <a href="https://redis.io/commands/sdiff">Redis Documentation: SDIFF</a>
     */
    public <V> Set<V> sDiff(final String key, final String otherKey) {
        return (Set<V>) setOps.difference(key, otherKey);
    }

    /**
     * 返回一个集合的全部成员，该集合是所有给定集合之间的差集。
     * 不存在的 key 被视为空集。
     *
     * @param otherKeys 键
     * @return 一个包含差集成员的列表。
     * @see <a href="https://redis.io/commands/sdiff">Redis Documentation: SDIFF</a>
     */
    public <V> Set<V> sDiff(Collection<String> otherKeys) {
        return (Set<V>) setOps.difference(otherKeys);
    }

    /**
     * 这个命令的作用和 SDIFF key [key …] 类似，但它将结果保存到 destination 集合，而不是简单地返回结果集。
     * 如果 destination 集合已经存在，则将其覆盖。
     * destination 可以是 key 本身。
     *
     * @param key      键
     * @param distKey  键
     * @param otherKey 键
     * @return 结果集中的元素数量。。
     * @see <a href="https://redis.io/commands/sdiffstore">Redis Documentation: sdiffstore</a>
     */
    public Long sDiffStore(final String key, final String otherKey, final String distKey) {
        return setOps.differenceAndStore(key, otherKey, distKey);
    }

    /**
     * 返回一个集合的全部成员，该集合是所有给定集合之间的差集。
     * 不存在的 key 被视为空集。
     *
     * @param otherKeys 键
     * @return 结果集中的元素数量。
     */
    public Long sDiffStore(Collection<String> otherKeys, final String distKey) {
        return setOps.differenceAndStore(otherKeys, distKey);
    }

    //===============================Set Start================================//

    //===============================List Start================================//

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return List
     */
    public <V> List<V> lGet(String key, long start, long end) {
        try {
            return (List<V>) listOps.range(key, start, end);
        } catch (Exception e) {
            log.error(" >>> Redis 获取list缓存的内容失败，{}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return long
     */
    public Long lGetListSize(String key) {
        try {
            return listOps.size(key);
        } catch (Exception e) {
            log.error(" >>> Redis 获取list缓存的长度失败，{}", e.getMessage(), e);
            return 0L;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引
     * @return Object
     */
    public <V> V lGetIndex(String key, long index) {
        try {
            return (V) listOps.index(key, index);
        } catch (Exception e) {
            log.error(" >>> Redis 通过索引获取list中的值失败，{}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key    键
     * @param values 值
     * @return 返回列表的长度
     */
    public Long lPush(String key, Object... values) {
        return listOps.leftPushAll(key, values);
    }

    /**
     * 将list放入缓存
     *
     * @param key    键
     * @param values 值
     * @return 返回列表的长度
     */
    public Long lPush(String key, Collection<Object> values) {
        return listOps.leftPushAll(key, values);
    }

    /**
     * 将值 value 插入到列表 key 的表头，当且仅当 key 存在并且是一个列表。
     * 和 LPUSH key value [value …] 命令相反，当 key 不存在时， LPUSHX 命令什么也不做
     *
     * @param key    键
     * @param values 值
     * @return 返回列表的长度
     * @see <a href="https://redis.io/commands/lpushx">Redis Documentation: LPUSHX</a>
     */
    public Long lPushX(final String key, Object values) {
        return listOps.leftPushIfPresent(key, values);
    }

    /**
     * 将list放入缓存
     *
     * @param key    键
     * @param values 值
     * @return 返回列表的长度
     */
    public Long rPush(String key, Object... values) {
        return listOps.rightPushAll(key, values);
    }

    /**
     * 将list放入缓存
     *
     * @param key    键
     * @param values 值
     * @return 返回列表的长度
     */
    public Long rPush(String key, Collection<Object> values) {
        return listOps.rightPushAll(key, values);
    }

    /**
     * 将值 value 插入到列表 key 的表头，当且仅当 key 存在并且是一个列表。
     * 和 LPUSH key value [value …] 命令相反，当 key 不存在时， LPUSHX 命令什么也不做
     *
     * @param key    键
     * @param values 值
     * @return 返回列表的长度
     * @see <a href="https://redis.io/commands/lpushx">Redis Documentation: LPUSHX</a>
     */
    public Long rPushX(final String key, Object values) {
        return listOps.rightPushIfPresent(key, values);
    }

    /**
     * 移除并返回列表 key 的头元素
     *
     * @param key 键
     * @return 列表的头元素。 当 key 不存在时，返回 nil 。
     * @see <a href="https://redis.io/commands/lpop">Redis Documentation: LPOP</a>
     */
    public <T> T lPop(final String key) {
        return (T) listOps.leftPop(key);
    }

    /**
     * 移除并返回列表 key 的尾元素。
     *
     * @param key 一定不能为  {@literal null}.
     * @return 列表的尾元素。 当 key 不存在时，返回 nil 。
     * @see <a href="https://redis.io/commands/rpop">Redis Documentation: RPOP</a>
     */
    public <T> T rPop(final String key) {
        return (T) listOps.rightPop(key);
    }

    /**
     * 命令 RPOPLPUSH 在一个原子时间内，执行以下两个动作：
     * 1.将列表 source 中的最后一个元素(尾元素)弹出，并返回给客户端。
     * 2. 将 source 弹出的元素插入到列表 destination ，作为 destination 列表的的头元素。
     * <p>
     * 举个例子，你有两个列表 source 和 destination ， source 列表有元素 a, b, c ， destination 列表有元素 x, y, z ，
     * 执行 RPOPLPUSH source destination 之后， source 列表包含元素 a, b ，
     * destination 列表包含元素 c, x, y, z ，并且元素 c 会被返回给客户端。
     * <p>
     * 如果 source 不存在，值 nil 被返回，并且不执行其他动作。
     * 如果 source 和 destination 相同，则列表中的表尾元素被移动到表头，并返回该元素，可以把这种特殊情况视作列表的旋转(rotation)操作。
     *
     * @param sourceKey      键
     * @param destinationKey 键
     * @return 被弹出的元素
     * @see <a href="https://redis.io/commands/rpoplpush">Redis Documentation: RPOPLPUSH</a>
     */
    public <T> T rPoplPush(String sourceKey, String destinationKey) {
        return (T) listOps.rightPopAndLeftPush(sourceKey, destinationKey);
    }

    /**
     * 根据参数 count 的值，移除列表中与参数 value 相等的元素。
     * <p>
     * count 的值可以是以下几种：
     * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
     * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
     * count = 0 : 移除表中所有与 value 相等的值。
     *
     * @param key   键
     * @param count 数量
     * @param value 值
     * @return 被移除元素的数量。 因为不存在的 key 被视作空表(empty list)，所以当 key 不存在时， LREM 命令总是返回 0 。
     * @see <a href="https://redis.io/commands/lrem">Redis Documentation: LREM</a>
     */
    public Long lRem(final String key, long count, Object value) {
        return listOps.remove(key, count, value);
    }

    /**
     * 返回列表 key 的长度。
     * 如果 key 不存在，则 key 被解释为一个空列表，返回 0 .
     * 如果 key 不是列表类型，返回一个错误。
     *
     * @param key 键
     * @return {列表 key 的长度
     * @see <a href="https://redis.io/commands/llen">Redis Documentation: LLEN</a>
     */
    public Long lLen(final String key) {
        return listOps.size(key);
    }

    /**
     * 返回列表 key 中，下标为 index 的元素。
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     * 如果 key 不是列表类型，返回一个错误。
     *
     * @param key   键
     * @param index 索引
     * @return 列表中下标为 index 的元素。 如果 index 参数的值不在列表的区间范围内(out of range)，返回 nil 。
     * @see <a href="https://redis.io/commands/lindex">Redis Documentation: LINDEX</a>
     */
    public <T> T lIndex(final String key, long index) {
        return (T) listOps.index(key, index);
    }

    /**
     * 将值 value 插入到列表 key 当中，位于值 pivot 之前。
     * 当 pivot 不存在于列表 key 时，不执行任何操作。
     * 当 key 不存在时， key 被视为空列表，不执行任何操作。
     * 如果 key 不是列表类型，返回一个错误。
     *
     * @param key   键
     * @param pivot 对比值
     * @param value 值
     * @return 如果命令执行成功，返回插入操作完成之后，列表的长度。 如果没有找到 pivot ，返回 -1 。 如果 key 不存在或为空列表，返回 0 。
     * @see <a href="https://redis.io/commands/linsert">Redis Documentation: LINSERT</a>
     */
    public Long lInsert(final String key, Object pivot, Object value) {
        return listOps.leftPush(key, pivot, value);
    }

    /**
     * 将值 value 插入到列表 key 当中，位于值 pivot 之后。
     * 当 pivot 不存在于列表 key 时，不执行任何操作。
     * 当 key 不存在时， key 被视为空列表，不执行任何操作。
     * 如果 key 不是列表类型，返回一个错误。
     *
     * @param key   键
     * @param pivot 对比值
     * @param value 值
     * @return 如果命令执行成功，返回插入操作完成之后，列表的长度。 如果没有找到 pivot ，返回 -1 。 如果 key 不存在或为空列表，返回 0 。
     * @see <a href="https://redis.io/commands/linsert">Redis Documentation: LINSERT</a>
     */
    public Long rInsert(final String key, Object pivot, Object value) {
        return listOps.rightPush(key, pivot, value);
    }

    /**
     * 将列表 key 下标为 index 的元素的值设置为 value 。
     * 当 index 参数超出范围，或对一个空列表( key 不存在)进行 LSET 时，返回一个错误。
     * 关于列表下标的更多信息，请参考 LINDEX 命令。
     *
     * @param key   键
     * @param index 下标
     * @param value 值
     * @see <a href="https://redis.io/commands/lset">Redis Documentation: LSET</a>
     */
    public void lSet(final String key, long index, Object value) {
        listOps.set(key, index, value);
    }

    /**
     * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     *
     * <pre>
     * 例子：
     * 获取 list 中所有数据：lRange(key, 0, -1);
     * 获取 list 中下标 1 到 3 的数据： lRange(key, 1, 3);
     * </pre>
     * <p>
     * 如果 start 下标比列表的最大下标 end ( LLEN list 减去 1 )还要大，那么 LRANGE 返回一个空列表。
     * 如果 stop 下标比 end 下标还要大，Redis将 stop 的值设置为 end 。
     *
     * @param key   键
     * @param start 开始索引
     * @param end   结束索引
     * @return 一个列表，包含指定区间内的元素。
     * @see <a href="https://redis.io/commands/lrange">Redis Documentation: LRANGE</a>
     */
    public List<Object> lRange(final String key, long start, long end) {
        return listOps.range(key, start, end);
    }

    /**
     * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
     * 举个例子，执行命令 LTRIM list 0 2 ，表示只保留列表 list 的前三个元素，其余元素全部删除。
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     * 当 key 不是列表类型时，返回一个错误。
     *
     * @param key   键
     * @param start 开始索引
     * @param end   结束索引
     * @see <a href="https://redis.io/commands/ltrim">Redis Documentation: LTRIM</a>
     */
    public void lTrim(final String key, long start, long end) {
        listOps.trim(key, start, end);
    }

    //===============================List End================================//

    //===============================zSet Start================================//

    /**
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
     * score 值可以是整数值或双精度浮点数。
     * 如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
     * 当 key 存在但不是有序集类型时，返回一个错误。
     *
     * @param key    键
     * @param score  得分
     * @param member 值
     * @return 是否成功
     * @see <a href="https://redis.io/commands/zadd">Redis Documentation: ZADD</a>
     */
    public Boolean zAdd(final String key, Object member, double score) {
        return zSetOps.add(key, member, score);
    }

    /**
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
     * score 值可以是整数值或双精度浮点数。
     * 如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
     * 当 key 存在但不是有序集类型时，返回一个错误。
     *
     * @param key          键
     * @param scoreMembers 键
     * @return 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员。
     * @see <a href="https://redis.io/commands/zadd">Redis Documentation: ZADD</a>
     */
    public Long zAdd(final String key, Map<Object, Double> scoreMembers) {
        Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<>();
        scoreMembers.forEach((score, member) -> tuples.add(new DefaultTypedTuple<>(score, member)));
        return zSetOps.add(key, tuples);
    }

    /**
     * 返回有序集 key 中，成员 member 的 score 值。
     * 如果 member 元素不是有序集 key 的成员，或 key 不存在，返回 nil 。
     *
     * @param key    键
     * @param member the value.
     * @return member 成员的 score 值，以字符串形式表示
     * @see <a href="https://redis.io/commands/zscore">Redis Documentation: ZSCORE</a>
     */
    public Double zScore(final String key, Object member) {
        return zSetOps.score(key, member);
    }

    /**
     * 为有序集 key 的成员 member 的 score 值加上增量 increment 。
     * 可以通过传递一个负数值 increment ，让 score 减去相应的值，比如 ZINCRBY key -5 member ，就是让 member 的 score 值减去 5 。
     * 当 key 不存在，或 member 不是 key 的成员时， ZINCRBY key increment member 等同于 ZADD key increment member 。
     * 当 key 不是有序集类型时，返回一个错误。
     * score 值可以是整数值或双精度浮点数。
     *
     * @param key    键
     * @param score  得分
     * @param member the value.
     * @return member 成员的新 score 值
     * @see <a href="https://redis.io/commands/zincrby">Redis Documentation: ZINCRBY</a>
     */
    public Double zIncrBy(final String key, Object member, double score) {
        return zSetOps.incrementScore(key, member, score);
    }

    /**
     * 返回有序集 key 的基数。
     *
     * @param key 键
     * @return 当 key 存在且是有序集类型时，返回有序集的基数。 当 key 不存在时，返回 0 。
     * @see <a href="https://redis.io/commands/zcard">Redis Documentation: ZCARD</a>
     */
    public Long zCard(final String key) {
        return zSetOps.zCard(key);
    }

    /**
     * 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zcount">Redis Documentation: ZCOUNT</a>
     */
    public Long zCount(final String key, double min, double max) {
        return zSetOps.count(key, min, max);
    }

    /**
     * 返回有序集 key 中，指定区间内的成员。
     * 其中成员的位置按 score 值递增(从小到大)来排序。
     * 具有相同 score 值的成员按字典序(lexicographical order )来排列。
     * <p>
     * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。 你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。
     * <p>
     * 超出范围的下标并不会引起错误。 比如说，当 start 的值比有序集的最大下标还要大，或是 start > stop 时， ZRANGE 命令只是简单地返回一个空列表。 另一方面，假如 stop
     * 参数的值比有序集的最大下标还要大，那么 Redis 将 stop 当作最大下标来处理。
     * <p>
     * 可以通过使用 WITHSCORES 选项，来让成员和它的 score 值一并返回，返回列表以 value1,score1, ..., valueN,scoreN 的格式表示。
     * 客户端库可能会返回一些更复杂的数据类型，比如数组、元组等
     *
     * @param key   键
     * @param start 索引
     * @param end   索引
     * @return 指定区间内，不带有 score 值(可选)的有序集成员的列表。
     * @see <a href="https://redis.io/commands/zrange">Redis Documentation: ZRANGE</a>
     */
    public Set<Object> zRange(final String key, long start, long end) {
        return zSetOps.range(key, start, end);
    }

    /**
     * 返回有序集 key 中，指定区间内的成员。
     * 其中成员的位置按 score 值递增(从小到大)来排序。
     * 具有相同 score 值的成员按字典序(lexicographical order )来排列。
     * <p>
     * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。 你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。
     * <p>
     * 超出范围的下标并不会引起错误。 比如说，当 start 的值比有序集的最大下标还要大，或是 start > stop 时， ZRANGE 命令只是简单地返回一个空列表。 另一方面，假如 stop
     * 参数的值比有序集的最大下标还要大，那么 Redis 将 stop 当作最大下标来处理。
     * <p>
     * 可以通过使用 WITHSCORES 选项，来让成员和它的 score 值一并返回，返回列表以 value1,score1, ..., valueN,scoreN 的格式表示。
     * 客户端库可能会返回一些更复杂的数据类型，比如数组、元组等
     *
     * @param key   键
     * @param start 索引
     * @param end   索引
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表。
     * @see <a href="https://redis.io/commands/zrange">Redis Documentation: ZRANGE</a>
     */
    public Set<ZSetOperations.TypedTuple<Object>> zRangeWithScores(final String key, long start, long end) {
        return zSetOps.rangeWithScores(key, start, end);
    }

    /**
     * 返回有序集 key 中，指定区间内的成员。
     * 其中成员的位置按 score 值递减(从大到小)来排列。 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order)排列。
     * 除了成员按 score 值递减的次序排列这一点外， ZREVRANGE 命令的其他方面和 ZRANGE key start stop [WITHSCORES] 命令一样。
     *
     * @param key   键
     * @param start 索引
     * @param end   索引
     * @return 指定区间内，不带有 score 值(可选)的有序集成员的列表。
     * @see <a href="https://redis.io/commands/zrevrange">Redis Documentation: ZREVRANGE</a>
     */
    public Set<Object> zRevrange(final String key, long start, long end) {
        return zSetOps.reverseRange(key, start, end);
    }

    /**
     * 返回有序集 key 中，指定区间内的成员。
     * 其中成员的位置按 score 值递减(从大到小)来排列。 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order)排列。
     * 除了成员按 score 值递减的次序排列这一点外， ZREVRANGE 命令的其他方面和 ZRANGE key start stop [WITHSCORES] 命令一样。
     *
     * @param key   键
     * @param start 索引
     * @param end   索引
     * @return 指定区间内，不带有 score 值(可选)的有序集成员的列表。
     * @see <a href="https://redis.io/commands/zrevrange">Redis Documentation: ZREVRANGE</a>
     */
    public Set<ZSetOperations.TypedTuple<Object>> zRevrangeWithScores(final String key, long start, long end) {
        return zSetOps.reverseRangeWithScores(key, start, end);
    }

    /**
     * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
     * 有序集成员按 score 值递增(从小到大)次序排列。
     * 具有相同 score 值的成员按字典序(lexicographical order)来排列(该属性是有序集提供的，不需要额外的计算)。
     *
     * @param key 键
     * @param min 最小得分
     * @param max 最大得分
     * @return 指定区间内 不带有 score 值(可选)的有序集成员的列表。
     * @see <a href="https://redis.io/commands/zrangebyscore">Redis Documentation: ZRANGEBYSCORE</a>
     */
    public Set<Object> zRangeByScore(final String key, double min, double max) {
        return zSetOps.rangeByScore(key, min, max);
    }

    /**
     * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
     * 有序集成员按 score 值递增(从小到大)次序排列。
     * 具有相同 score 值的成员按字典序(lexicographical order)来排列(该属性是有序集提供的，不需要额外的计算)。
     *
     * @param key 键
     * @param min 最小得分
     * @param max 最大得分
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表。
     * @see <a href="https://redis.io/commands/zrangebyscore">Redis Documentation: ZRANGEBYSCORE</a>
     */
    public Set<ZSetOperations.TypedTuple<Object>> zRangeByScoreWithScores(final String key, double min, double max) {
        return zSetOps.rangeByScoreWithScores(key, min, max);
    }

    /**
     * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。有序集成员按 score 值递减(从大到小)的次序排列。
     * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order )排列。
     *
     * @param key 键
     * @param min 最小得分
     * @param max 最大得分
     * @return 指定区间内 不带有 score 值(可选)的有序集成员的列表。
     * @see <a href="https://redis.io/commands/zrevrange">Redis Documentation: ZRANGEBYSCORE</a>
     */
    public Set<Object> zReverseRange(final String key, double min, double max) {
        return zSetOps.reverseRangeByScore(key, min, max);
    }

    /**
     * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。有序集成员按 score 值递减(从大到小)的次序排列。
     * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order )排列。
     *
     * @param key 键
     * @param min 最小得分
     * @param max 最大得分
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表。
     * @see <a href="https://redis.io/commands/zrevrangebyscore">Redis Documentation: ZRANGEBYSCORE</a>
     */
    public Set<ZSetOperations.TypedTuple<Object>> zReverseRangeByScoreWithScores(final String key, double min, double max) {
        return zSetOps.reverseRangeByScoreWithScores(key, min, max);
    }

    /**
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列。
     * 排名以 0 为底，也就是说， score 值最小的成员排名为 0 。
     * 使用 ZREVRANK key member 命令可以获得成员按 score 值递减(从大到小)排列的排名。
     *
     * @param key    键
     * @param member the value.
     * @return 如果 member 是有序集 key 的成员，返回 member 的排名。 如果 member 不是有序集 key 的成员，返回 nil 。
     * @see <a href="https://redis.io/commands/zrank">Redis Documentation: ZRANK</a>
     */
    public Long zRank(final String key, Object member) {
        return zSetOps.rank(key, member);
    }

    /**
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序。
     * 排名以 0 为底，也就是说， score 值最大的成员排名为 0 。
     * 使用 ZRANK 命令可以获得成员按 score 值递增(从小到大)排列的排名。
     *
     * @param key    键
     * @param member the value.
     * @return 如果 member 是有序集 key 的成员，返回 member 的排名。 如果 member 不是有序集 key 的成员，返回 nil 。
     * @see <a href="https://redis.io/commands/zrevrank">Redis Documentation: ZREVRANK</a>
     */
    public Long zRevrank(final String key, Object member) {
        return zSetOps.reverseRank(key, member);
    }

    /**
     * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略。
     * 当 key 存在但不是有序集类型时，返回一个错误。
     *
     * @param key     键
     * @param members 键
     * @return 被成功移除的成员的数量，不包括被忽略的成员
     * @see <a href="https://redis.io/commands/zrem">Redis Documentation: ZREM</a>
     */
    public Long zRem(final String key, Object... members) {
        return zSetOps.remove(key, members);
    }

    /**
     * 移除有序集 key 中，指定排名(rank)区间内的所有成员。
     * 区间分别以下标参数 start 和 stop 指出，包含 start 和 stop 在内。
     * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。 你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。
     *
     * @param key   键
     * @param start 下标
     * @param end   下标
     * @return 被移除成员的数量。
     * @see <a href="https://redis.io/commands/zremrangebyrank">Redis Documentation: ZREMRANGEBYRANK</a>
     */
    public Long zRem(final String key, long start, long end) {
        return zSetOps.removeRange(key, start, end);
    }

    /**
     * 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
     * 自版本2.1.6开始， score 值等于 min 或 max 的成员也可以不包括在内
     *
     * @param key 键
     * @param min 最小得分
     * @param max 最大得分
     * @return 被移除成员的数量。
     * @see <a href="https://redis.io/commands/zremrangebyscore">Redis Documentation: ZREMRANGEBYSCORE</a>
     */
    public Long zRemRangeByScore(final String key, double min, double max) {
        return zSetOps.removeRangeByScore(key, min, max);
    }

}
