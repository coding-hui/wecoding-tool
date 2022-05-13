package top.wecoding.core.jwt.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import top.wecoding.core.constant.SecurityConstants;
import top.wecoding.core.constant.StrPool;
import top.wecoding.core.constant.TokenConstant;
import top.wecoding.core.exception.code.ClientErrorCodeEnum;
import top.wecoding.core.exception.user.UnauthorizedException;
import top.wecoding.core.jwt.model.TokenInfo;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

/**
 * Jwt工具类
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Slf4j
public class JwtUtils {

    /**
     * 签名加密
     */
    public static String getBase64Security() {
        return Base64.getEncoder().encodeToString(TokenConstant.SING_KEY.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 创建 JWT 令牌
     *
     * @param claims 数据声明
     * @param expire 过期时间（秒)
     * @return 令牌
     */
    public static TokenInfo createJWT(Map<String, String> claims, long expire) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 生成签名密钥
        byte[] apiKeySecretBytes = Base64.getDecoder().decode(getBase64Security());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JsonWebToken")
                .setIssuedAt(now)
                .signWith(signingKey, signatureAlgorithm);

        // 设置JWT数据声明
        claims.forEach(builder::claim);

        // 添加Token过期时间
        long expMillis = nowMillis + expire * 1000;
        Date exp = new Date(expMillis);
        builder.setNotBefore(now).setExpiration(exp);

        // 组装Token信息
        return TokenInfo.builder()
                .token(builder.compact())
                .expiresIn(expire)
                .expiration(DateUtil.toLocalDateTime(exp))
                .build();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public static Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Base64.getDecoder().decode(getBase64Security()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            throw new UnauthorizedException(ClientErrorCodeEnum.JWT_TOKEN_EXPIRED);
        } catch (SignatureException ex) {
            throw new UnauthorizedException(ClientErrorCodeEnum.JWT_SIGNATURE_ERROR);
        } catch (IllegalArgumentException ex) {
            throw new UnauthorizedException(ClientErrorCodeEnum.JWT_TOKEN_IS_EMPTY);
        } catch (Exception e) {
            throw new UnauthorizedException(ClientErrorCodeEnum.JWT_PARSE_TOKEN_ERROR);
        }
    }

    /**
     * 校验token是否失效
     */
    public static boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            final Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception expiredJwtException) {
            return true;
        }
    }

    /**
     * 根据令牌获取用户key
     *
     * @param token 令牌
     * @return 用户key
     */
    public static String getUserKey(String token) {
        Claims claims = parseToken(token);
        return getValue(claims, SecurityConstants.USER_KEY);
    }

    /**
     * 根据身份信息获取用户key
     *
     * @param claims 身份信息
     * @return 用户key
     */
    public static String getUserKey(Claims claims) {
        return getValue(claims, SecurityConstants.USER_KEY);
    }

    /**
     * 根据令牌获取用户ID
     *
     * @param token 令牌
     * @return 用户ID
     */
    public static Long getUserId(String token) {
        Claims claims = parseToken(token);
        return Convert.toLong(getValue(claims, SecurityConstants.DETAILS_USER_ID));
    }

    /**
     * 根据身份信息获取用户ID
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    public static Long getUserId(Claims claims) {
        return Convert.toLong(getValue(claims, SecurityConstants.DETAILS_USER_ID));
    }

    /**
     * 根据身份信息获取用户账号
     *
     * @param claims 身份信息
     * @return 用户账号
     */
    public static String getClientId(Claims claims) {
        return getValue(claims, SecurityConstants.DETAILS_CLIENT_ID);
    }

    /**
     * 根据令牌获取用户名字
     *
     * @param token 令牌
     * @return 用户名字
     */
    public static String getClientId(String token) {
        Claims claims = parseToken(token);
        return getValue(claims, SecurityConstants.DETAILS_CLIENT_ID);
    }

    /**
     * 根据令牌获取用户账号
     *
     * @param token 令牌
     * @return 用户ID
     */
    public static String getUserAccount(String token) {
        Claims claims = parseToken(token);
        return getValue(claims, SecurityConstants.DETAILS_ACCOUNT);
    }

    /**
     * 根据身份信息获取用户账号
     *
     * @param claims 身份信息
     * @return 用户账号
     */
    public static String getUserAccount(Claims claims) {
        return getValue(claims, SecurityConstants.DETAILS_ACCOUNT);
    }

    /**
     * 根据身份信息获取用户名字
     *
     * @param token 令牌
     * @return 用户名字
     */
    public static String getUserRealName(String token) {
        Claims claims = parseToken(token);
        return getValue(claims, SecurityConstants.DETAILS_ACCOUNT);
    }

    /**
     * 根据身份信息获取用户名字
     *
     * @param claims 身份信息
     * @return 用户名字
     */
    public static String getUserRealName(Claims claims) {
        return getValue(claims, SecurityConstants.DETAILS_USERNAME);
    }

    /**
     * 从身份信息获取键值，找不到返回空
     *
     * @param claims 身份信息
     * @param key    键
     * @return 值
     */
    public static String getValue(Claims claims, String key) {
        return Convert.toStr(claims.get(key), StrPool.EMPTY);
    }

}