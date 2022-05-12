package top.wecoding.core.security.service;

import top.wecoding.core.jwt.model.TokenInfo;
import top.wecoding.core.model.LoginUser;
import top.wecoding.core.security.provider.ClientDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录校验服务接口
 *
 * @author liuyuhui
 * @qq 1515418211
 */
public interface TokenService {

    /**
     * 登录验证，授予 Token
     *
     * @param loginUser     登录用户信息
     * @param clientDetails 客户端信息
     * @param tokenType     令牌类型
     * @return token
     */
    TokenInfo generateToken(LoginUser loginUser, ClientDetails clientDetails, String tokenType);

    /**
     * 登录验证，授予 Token
     *
     * @param loginUser     登录用户信息
     * @param clientDetails 客户端信息
     * @return refresh token
     */
    String generateRefreshToken(LoginUser loginUser, ClientDetails clientDetails);

    /**
     * 从请求中获取用户身份信息
     *
     * @param request 请求
     * @return 用户信息
     */
    LoginUser getLoginUser(HttpServletRequest request);

    /**
     * 从令牌中获取用户身份信息
     *
     * @param token 令牌
     * @return 用户信息
     */
    LoginUser getLoginUser(String token);

    /**
     * 验证令牌有效期，没有过期则自动刷新
     *
     * @param token 令牌
     */
    void verifyToken(String token);

    /**
     * 刷新令牌有效期
     *
     * @param token 令牌
     */
    void refreshToken(String token);

    /**
     * 删除用户身份信息
     */
    void removeLoginUser(String token);

}
