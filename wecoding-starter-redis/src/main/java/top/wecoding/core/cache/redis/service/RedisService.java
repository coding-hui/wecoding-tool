package top.wecoding.core.cache.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.lang.NonNull;
import top.wecoding.core.exception.Assert;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * spring redis 工具类
 *
 * @author liuyuhui
 **/
@Slf4j
@SuppressWarnings({"unused", "SpellCheckingInspection", "unchecked"})
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ValueOperations<String, Object> valueOps;
    private final HashOperations<String, Object, Object> hashOps;
    private final ListOperations<String, Object> listOps;
    private final SetOperations<String, Object> setOps;

    public RedisService(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        Assert.notNull(redisTemplate, " >>> redisTemplate 不能为空.");
        this.redisTemplate = redisTemplate;
        this.valueOps = redisTemplate.opsForValue();
        this.hashOps = redisTemplate.opsForHash();
        this.listOps = redisTemplate.opsForList();
        this.setOps = redisTemplate.opsForSet();
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public Boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
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
     * 获取有效时间
     *
     * @param key Redis键
     * @return 有效时间
     */
    public Long getExpire(final String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     *
     * @param key 缓存 keys
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
        }
    }

    /**
     * 删除缓存
     *
     * @param keys 缓存 keys
     */
    public void del(Collection<String> keys) {
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
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
     * @see <a href="https://redis.io/commands/keys">Redis Documentation: KEYS</a>
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 返回所有(一个或多个)给定 key 的值, 值按请求的键的顺序返回。
     * 如果给定的 key 里面，有某个 key 不存在，那么这个 key 返回特殊值 nil
     *
     * @param keys 一定不能为 {@literal null}.
     * @return 返回一个列表， 列表中包含了所有给定键的值,并按给定key的顺序排列
     * @see <a href="https://redis.io/commands/mget">Redis Documentation: MGET</a>
     */
    public <T> List<T> mGet(String... keys) {
        return mGet(Arrays.asList(keys));
    }

    /**
     * 返回所有(一个或多个)给定 key 的值, 值按请求的键的顺序返回。
     * 如果给定的 key 里面，有某个 key 不存在，那么这个 key 返回特殊值 nil
     *
     * @param keys 一定不能为 {@literal null}.
     * @return 返回一个列表， 列表中包含了所有给定键的值,并按给定key的顺序排列
     * @see <a href="https://redis.io/commands/mget">Redis Documentation: MGET</a>
     */
    public <T> List<T> mGet(@NonNull Collection<String> keys) {
        List<T> list = (List<T>) valueOps.multiGet(keys);
        return list == null ? Collections.emptyList() : list;
    }

    //============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public <V> V get(String key) {
        return key == null ? null : (V) valueOps.get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public Boolean set(String key, Object value) {
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
     * @return true成功 false 失败
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
     * @return true成功 false 失败
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
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return long
     */
    public Long incr(String key, long delta) {
        Assert.isTrue(delta < 0, "递增因子必须大于0");
        return valueOps.increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return long
     */
    public Long decr(String key, long delta) {
        Assert.isTrue(delta < 0, "递增因子必须大于0");
        return valueOps.increment(key, -delta);
    }

    //================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public <V> V hget(String key, String item) {
        return (V) hashOps.get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return hashOps.entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public Boolean hmset(String key, Map<String, Object> map) {
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
     * @return true成功 false失败
     */
    public Boolean hmset(String key, Map<String, Object> map, long time) {
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
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public Boolean hset(String key, String item, Object value) {
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
    public Boolean hset(String key, String item, Object value, long time) {
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
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {
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
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return double
     */
    public double hincr(String key, String item, double by) {
        return hashOps.increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return double
     */
    public double hdecr(String key, String item, double by) {
        return hashOps.increment(key, item, -by);
    }

    //============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return Set
     */
    public <V> Set<V> sGet(String key) {
        try {
            return (Set<V>) setOps.members(key);
        } catch (Exception e) {
            log.error(" >>> 获取 Redis Set失败，{}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public Boolean sHasKey(String key, Object value) {
        try {
            return setOps.isMember(key, value);
        } catch (Exception e) {
            log.error(" >>> 获取 Redis Set失败，{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long sSet(String key, Object... values) {
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
    public Long sSetAndTime(String key, long time, Object... values) {
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
     * 获取set缓存的长度
     *
     * @param key 键
     * @return long
     */
    public Long sGetSetSize(String key) {
        try {
            return setOps.size(key);
        } catch (Exception e) {
            log.error(" >>> Redis 获取set缓存的长度失败，{}", e.getMessage(), e);
            return 0L;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public Long setRemove(String key, Object... values) {
        try {
            return setOps.remove(key, values);
        } catch (Exception e) {
            log.error(" >>> Redis 移除值为value的失败，{}", e.getMessage(), e);
            return 0L;
        }
    }
    //===============================list=================================

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
     * @param key   键
     * @param value 值
     * @return Boolean
     */
    public Boolean lSet(String key, Object value) {
        try {
            listOps.rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error(" >>> Redis 将list放入缓存失败，{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return Boolean
     */
    public Boolean lSet(String key, Object value, long time) {
        try {
            listOps.rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error(" >>> Redis 将list放入缓存失败，{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return Boolean
     */
    public Boolean lSet(String key, List<Object> value) {
        try {
            listOps.rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error(" >>> Redis 将list放入缓存失败，{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return Boolean
     */
    public Boolean lSet(String key, List<Object> value, long time) {
        try {
            listOps.rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error(" >>> Redis 将list放入缓存失败，{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return Boolean
     */
    public Boolean lUpdateIndex(String key, long index, Object value) {
        try {
            listOps.set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error(" >>> Redis 根据索引修改list中的数据失败，{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public Long lRemove(String key, long count, Object value) {
        try {
            return listOps.remove(key, count, value);
        } catch (Exception e) {
            log.error(" >>> Redis 移除 list 缓存失败，{}", e.getMessage(), e);
            return 0L;
        }
    }

}
