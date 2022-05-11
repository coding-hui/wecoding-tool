package top.wecoding.core.cache;

import top.wecoding.core.cache.base.BaseMemoryCacheOperator;

/**
 * @author liuyuhui
 * @date 2021/07/30
 * @qq 1515418211
 */
public class SystemConfigCacheOperator extends BaseMemoryCacheOperator<String> {

    public static final String SYSTEM_CONFIG_CACHE_PREFIX = "system_config_";

    @Override
    public String getKeyPrefix() {
        return SYSTEM_CONFIG_CACHE_PREFIX;
    }

}
