package top.wecoding.core.security.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import top.wecoding.core.cache.UserCache;
import top.wecoding.core.constant.SecurityConstants;
import top.wecoding.core.constant.TokenConstant;
import top.wecoding.core.exception.code.ClientErrorCodeEnum;
import top.wecoding.core.exception.user.UnauthorizedException;
import top.wecoding.core.jwt.model.JwtPayLoad;
import top.wecoding.core.jwt.model.TokenInfo;
import top.wecoding.core.jwt.util.JwtUtils;
import top.wecoding.core.model.LoginUser;
import top.wecoding.core.security.provider.ClientDetails;
import top.wecoding.core.security.service.TokenService;
import top.wecoding.core.security.util.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 登录校验服务接口实现
 *
 * @author liuyuhui
 * @date 2021/07/30
 * @qq 1515418211
 */
@Component
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final UserCache userCache;

    @Override
    public TokenInfo generateToken(LoginUser loginUser, ClientDetails clientDetails, String tokenType) {
        String uuid = IdUtil.fastSimpleUUID();
        Long userId = loginUser.getUserId();
        String account = loginUser.getAccount();
        loginUser.setUuid(uuid);
        loginUser.setClientId(clientDetails.getClientId());
        loginUser.setLastLoginTime(new Date());
        // 添加Token过期时间
        long expireMillis = clientDetails.getAccessTokenValiditySeconds();
        JwtPayLoad jwtPayLoad = JwtPayLoad.builder()
                .tokenType(TokenConstant.ACCESS_TOKEN)
                .clientId(clientDetails.getClientId())
                .userId(userId)
                .account(account)
                .uuid(uuid)
                .expireMillis(expireMillis)
                .build();
        String accessToken = JwtUtils.createToken(jwtPayLoad);

        // 缓存登录用户信息
        cacheLoginUser(jwtPayLoad, loginUser);

        // 构建 Token 返回信息
        return TokenInfo.builder()
                .accessToken(accessToken)
                .refreshToken(generateRefreshToken(loginUser, clientDetails))
                .tokenType(tokenType)
                .expiresIn(expireMillis)
                .userId(userId)
                .avatar(loginUser.getUserType())
                .account(account)
                .license(SecurityConstants.PROJECT_LICENSE)
                .build();
    }

    @Override
    public String generateRefreshToken(LoginUser loginUser, ClientDetails clientDetails) {
        JwtPayLoad jwtPayLoad = JwtPayLoad.builder()
                .tokenType(TokenConstant.REFRESH_TOKEN)
                .clientId(clientDetails.getClientId())
                .userId(loginUser.getUserId())
                .account(loginUser.getAccount())
                .expireMillis(clientDetails.getRefreshTokenValiditySeconds())
                .build();
        return JwtUtils.createToken(jwtPayLoad);
    }

    @Override
    public LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = SecurityUtils.getToken(request);
        if (StrUtil.isBlank(token)) {
            return null;
        }
        return getLoginUser(token);
    }

    @Override
    public LoginUser getLoginUser(String token) {
        // 检验 token
        verifyToken(token);

        // 从缓存中获取登录用户
        LoginUser loginUser = userCache.get(JwtUtils.getUserKey(token));

        // 用户不存在则表示登录已过期
        if (ObjectUtil.isNull(loginUser)) {
            throw new UnauthorizedException(ClientErrorCodeEnum.LOGIN_EXPIRED);
        }

        return loginUser;
    }

    @Override
    public void verifyToken(String token) {
        // 校验token是否正确
        if (!JwtUtils.checkToken(token)) {
            throw new UnauthorizedException(ClientErrorCodeEnum.VALID_TOKEN_ERROR);
        }
        // 校验token是否失效
        if (JwtUtils.isTokenExpired(token)) {
            throw new UnauthorizedException(ClientErrorCodeEnum.LOGIN_EXPIRED);
        }
    }

    @Override
    public void refreshToken(String token) {

    }

    @Override
    public void removeLoginUser(String token) {
        if (StrUtil.isBlank(token)) {
            return;
        }

        String userKey = JwtUtils.getUserKey(token);
        userCache.remove(userKey);
    }

    /**
     * 缓存token与登录用户信息对应, 默认2个小时
     */
    private void cacheLoginUser(JwtPayLoad jwtPayLoad, LoginUser loginUser) {
        String redisLoginUserKey = jwtPayLoad.getUuid();
        userCache.put(redisLoginUserKey, loginUser, jwtPayLoad.getExpireMillis());
    }

}
