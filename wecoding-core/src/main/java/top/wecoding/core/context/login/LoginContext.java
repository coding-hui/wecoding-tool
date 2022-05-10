package top.wecoding.core.context.login;

import top.wecoding.core.enums.UserTypeEnum;
import top.wecoding.core.model.LoginUser;

/**
 * 登录用户上下文接口
 *
 * @author liuyuhui
 * @qq 1515418211
 */
public interface LoginContext {

    /**
     * 获取用户缓存Key
     *
     * @return cache key
     */
    String getUserCacheKey();

    /**
     * 获取客户端 ID
     *
     * @return clientId
     */
    String getClientId();

    /**
     * 获取登录用户账户
     *
     * @return 登录用户账户
     **/
    String getAccount();

    /**
     * 获取登录用户 ID
     *
     * @return UserID
     */
    Long getUserId();

    /**
     * 获取登录用户，获取不到抛出异常
     *
     * @return LoginUser
     */
    LoginUser getLoginUser();

    /**
     * 获取登录用户，获取不到不抛出异常
     *
     * @return LoginUser
     */
    LoginUser getLoginUserWithoutException();

    /**
     * 判断当前用户是否是管理员
     *
     * @return 结果
     */
    boolean isAdmin();

    /**
     * 判断当前登录用户是否是指定用户类型
     *
     * @param userType 用户类型
     * @return 结果
     */
    boolean isUserType(UserTypeEnum userType);

}
