package top.wecoding.core.cache;

import top.wecoding.core.cache.base.BaseMemoryCacheOperator;

/**
 * @author ffd
 * @create 2022-01-21
 * @Description 验证码缓存（登录图形验证码，短信邮箱验证码通用）
 */
public class ValidateCacheOperator extends BaseMemoryCacheOperator<String> {

    public static final String VALIDATE_CODE_CACHE_PREFIX = "validate_";

    @Override
    public String getKeyPrefix() {
        return VALIDATE_CODE_CACHE_PREFIX;
    }

}
