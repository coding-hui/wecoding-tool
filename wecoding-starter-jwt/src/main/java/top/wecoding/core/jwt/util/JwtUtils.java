package top.wecoding.core.jwt.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import top.wecoding.core.constant.SecurityConstants;
import top.wecoding.core.constant.StrPool;
import top.wecoding.core.constant.TokenConstant;
import top.wecoding.core.exception.code.ClientErrorCodeEnum;
import top.wecoding.core.exception.user.UnauthorizedException;
import top.wecoding.core.jwt.model.JwtPayLoad;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt工具类
 *
 * @author liuyuhui
 * @qq 1515418211
 */
public class JwtUtils {

    /**
     * 创建 JWT 令牌
     *
     * @param jwtPayLoad 数据声明
     * @return 令牌
     */
    public static String createToken(JwtPayLoad jwtPayLoad) {
        DateTime expirationDate = DateUtil.offsetSecond(new Date(),
                Convert.toInt(jwtPayLoad.getExpireMillis()));

        Map<String, Object> claims = new HashMap<>();
        if (StrUtil.isNotBlank(jwtPayLoad.getTokenType())) {
            claims.put(TokenConstant.TOKEN_TYPE, jwtPayLoad.getTokenType());
        }
        if (StrUtil.isNotBlank(jwtPayLoad.getUuid())) {
            claims.put(SecurityConstants.USER_KEY, jwtPayLoad.getUuid());
        }
        if (ObjectUtil.isNotNull(jwtPayLoad.getUserId())) {
            claims.put(SecurityConstants.DETAILS_USER_ID, jwtPayLoad.getUserId());
        }
        if (StrUtil.isNotBlank(jwtPayLoad.getAccount())) {
            claims.put(SecurityConstants.DETAILS_ACCOUNT, jwtPayLoad.getAccount());
        }
        if (StrUtil.isNotBlank(jwtPayLoad.getClientId())) {
            claims.put(SecurityConstants.DETAILS_CLIENT_ID, jwtPayLoad.getClientId());
        }

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(jwtPayLoad.getUserId().toString())
                .setIssuedAt(new Date())
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, TokenConstant.SING_KEY);
        return jwtBuilder.compact();
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
                    .setSigningKey(Base64.getDecoder().decode(TokenConstant.SING_KEY))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new UnauthorizedException(ClientErrorCodeEnum.PARSE_TOKEN_ERROR);
        }
    }

    /**
     * 校验token是否正确
     */
    public static boolean checkToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception jwtException) {
            return false;
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
    public static String getUserId(String token) {
        Claims claims = parseToken(token);
        return getValue(claims, SecurityConstants.DETAILS_USER_ID);
    }

    /**
     * 根据身份信息获取用户ID
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    public static String getUserId(Claims claims) {
        return getValue(claims, SecurityConstants.DETAILS_USER_ID);
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