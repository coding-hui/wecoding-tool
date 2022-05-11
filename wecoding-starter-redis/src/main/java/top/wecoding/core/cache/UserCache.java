package top.wecoding.core.cache;

import lombok.extern.slf4j.Slf4j;
import top.wecoding.core.cache.base.BaseRedisCacheOperator;
import top.wecoding.core.cache.constant.CacheConstants;
import top.wecoding.core.cache.redis.service.RedisService;
import top.wecoding.core.model.LoginUser;

/**
 * @author liuyuhui
 * @date 2022
 * @qq 1515418211
 */
@Slf4j
public class UserCache extends BaseRedisCacheOperator<LoginUser> {

    public UserCache(RedisService redisService) {
        super(redisService);
    }

    @Override
    public String getKeyPrefix() {
        return CacheConstants.LOGIN_USER_CACHE;
    }

}
