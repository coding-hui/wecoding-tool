package top.wecoding.core.security.cache;

import top.wecoding.core.auth.model.LoginUser;
import top.wecoding.core.cache.base.BaseRedisCacheOperator;
import top.wecoding.core.cache.redis.service.RedisService;

/**
 * 登录用户信息缓存
 *
 * @author liuyuhui
 * @date 2022/5/18
 * @qq 1515418211
 */
public class LoginUserCache extends BaseRedisCacheOperator<LoginUser> {

    public static final String LOGIN_USER_CACHE = "login_user:";

    public LoginUserCache(RedisService redisService) {
        super(redisService);
    }

    @Override
    public String getKeyPrefix() {
        return LOGIN_USER_CACHE;
    }

}
