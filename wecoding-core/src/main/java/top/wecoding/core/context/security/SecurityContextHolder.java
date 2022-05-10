package top.wecoding.core.context.security;

import cn.hutool.core.convert.Convert;
import com.alibaba.ttl.TransmittableThreadLocal;
import top.wecoding.core.constant.SecurityConstants;
import top.wecoding.core.constant.StrPool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 获取当前线程变量中的 用户id、用户名称、Token等信息
 *
 * @author liuyuhui
 * @qq 1515418211
 */
public class SecurityContextHolder {

    private static final TransmittableThreadLocal<Map<String, Object>> THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static void set(String key, Object value) {
        Map<String, Object> map = getLocalMap();
        map.put(key, value == null ? StrPool.EMPTY : value);
    }

    public static String get(String key) {
        Map<String, Object> map = getLocalMap();
        return Convert.toStr(map.getOrDefault(key, StrPool.EMPTY));
    }

    public static <T> T get(String key, Class<T> clazz) {
        Map<String, Object> map = getLocalMap();
        return Convert.convert(clazz, map.getOrDefault(key, null));
    }

    public static Map<String, Object> getLocalMap() {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new ConcurrentHashMap<>();
            THREAD_LOCAL.set(map);
        }
        return map;
    }

    public static void setLocalMap(Map<String, Object> threadLocalMap) {
        THREAD_LOCAL.set(threadLocalMap);
    }

    public static Long getUserId() {
        return Convert.toLong(get(SecurityConstants.DETAILS_USER_ID), 0L);
    }

    public static void setUserId(Long userId) {
        set(SecurityConstants.DETAILS_USER_ID, userId);
    }

    public static void setUserId(String userId) {
        set(SecurityConstants.DETAILS_USER_ID, userId);
    }

    public static String getUserIdStr() {
        return Convert.toStr(getUserId());
    }

    public static String getAccount() {
        return get(SecurityConstants.DETAILS_ACCOUNT, String.class);
    }

    public static void setAccount(String account) {
        set(SecurityConstants.DETAILS_ACCOUNT, account);
    }

    public static String getRealName() {
        return get(SecurityConstants.DETAILS_USERNAME, String.class);
    }

    public static void setRealName(String name) {
        set(SecurityConstants.DETAILS_USERNAME, name);
    }

    public static String getClientId() {
        return get(SecurityConstants.DETAILS_CLIENT_ID, String.class);
    }

    public static void setClientId(String clientId) {
        set(SecurityConstants.DETAILS_CLIENT_ID, clientId);
    }

    public static String getUserKey() {
        return get(SecurityConstants.USER_KEY);
    }

    public static void setUserKey(String userKey) {
        set(SecurityConstants.USER_KEY, userKey);
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

}
