package top.wecoding.core.jwt.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.jsonwebtoken.Claims;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import top.wecoding.core.constant.SecurityConstants;
import top.wecoding.core.constant.StrPool;
import top.wecoding.core.constant.TokenConstant;
import top.wecoding.core.jwt.model.AuthInfo;
import top.wecoding.core.jwt.model.JwtPayLoad;
import top.wecoding.core.jwt.model.TokenInfo;
import top.wecoding.core.jwt.props.JwtProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Token相关工具类
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Slf4j
@UtilityClass
public class TokenUtil {

    private static JwtProperties jwtProperties;

    public static JwtProperties getJwtProperties() {
        if (jwtProperties == null) {
            jwtProperties = SpringUtil.getBean(JwtProperties.class);
        }
        return jwtProperties;
    }

    /**
     * 去掉 Token 前缀
     */
    public String replaceTokenPrefix(String token) {
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StrUtil.isNotEmpty(token) && token.startsWith(TokenConstant.PREFIX)) {
            token = token.replaceFirst(TokenConstant.PREFIX, StrPool.EMPTY);
        }
        return token;
    }

    /**
     * 创建认证 Token
     *
     * @param jwtPayLoad   数据声明
     * @param expireMillis 过期时间（秒）
     * @return 认证信息
     */
    public AuthInfo createAuthInfo(JwtPayLoad jwtPayLoad, Long expireMillis) {
        if (expireMillis == null || expireMillis <= 0) {
            expireMillis = getJwtProperties().getExpire();
        }

        Map<String, String> claims = new HashMap<>();
        claims.put(TokenConstant.TOKEN_TYPE, TokenConstant.ACCESS_TOKEN);
        claims.put(SecurityConstants.USER_KEY, jwtPayLoad.getUuid());
        claims.put(SecurityConstants.DETAILS_USER_ID, Convert.toStr(jwtPayLoad.getUserId()));
        claims.put(SecurityConstants.DETAILS_ACCOUNT, jwtPayLoad.getAccount());
        claims.put(SecurityConstants.DETAILS_CLIENT_ID, jwtPayLoad.getClientId());
        claims.put(SecurityConstants.DETAILS_USERNAME, jwtPayLoad.getRealName());

        TokenInfo tokenInfo = JwtUtils.createJWT(claims, expireMillis);

        return AuthInfo.builder()
                .accessToken(tokenInfo.getToken())
                .expireMillis(tokenInfo.getExpiresIn())
                .expiration(tokenInfo.getExpiration())
                .tokenType(TokenConstant.ACCESS_TOKEN)
                .refreshToken(createRefreshToken(jwtPayLoad).getToken())
                .uuid(jwtPayLoad.getUuid())
                .userId(jwtPayLoad.getUserId())
                .account(jwtPayLoad.getAccount())
                .realName(jwtPayLoad.getRealName())
                .clientId(jwtPayLoad.getClientId())
                .build();
    }

    /**
     * 创建refreshToken
     *
     * @param jwtPayLoad 数据声明
     * @return refreshToken
     */
    private TokenInfo createRefreshToken(JwtPayLoad jwtPayLoad) {
        Map<String, String> claims = new HashMap<>(16);
        claims.put(TokenConstant.TOKEN_TYPE, TokenConstant.REFRESH_TOKEN);
        claims.put(SecurityConstants.USER_KEY, jwtPayLoad.getUuid());
        claims.put(SecurityConstants.DETAILS_USER_ID, Convert.toStr(jwtPayLoad.getUserId()));
        claims.put(SecurityConstants.DETAILS_ACCOUNT, jwtPayLoad.getAccount());
        claims.put(SecurityConstants.DETAILS_CLIENT_ID, jwtPayLoad.getClientId());
        return JwtUtils.createJWT(claims, getJwtProperties().getRefreshExpire());
    }

    /**
     * 解析 Token
     *
     * @param token token
     * @return 用户信息
     */
    public AuthInfo getAuthInfo(String token) {
        Claims claims = JwtUtils.parseToken(replaceTokenPrefix(token));
        Date expiration = claims.getExpiration();
        return AuthInfo.builder()
                .accessToken(token)
                .tokenType(JwtUtils.getValue(claims, TokenConstant.TOKEN_TYPE))
                .uuid(JwtUtils.getUserKey(claims))
                .userId(JwtUtils.getUserId(claims))
                .account(JwtUtils.getUserAccount(claims))
                .realName(JwtUtils.getUserRealName(claims))
                .clientId(JwtUtils.getClientId(claims))
                .expireMillis(Convert.toLong(expiration, 0L))
                .build();
    }

}
