/*
 * Copyright (c) 2022. WeCoding (wecoding@yeah.net).
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/lgpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.wecoding.core.jwt.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import top.wecoding.core.constant.SecurityConstants;
import top.wecoding.core.constant.StrPool;
import top.wecoding.core.exception.ArgumentException;
import top.wecoding.core.exception.Assert;
import top.wecoding.core.exception.code.ClientErrorCodeEnum;
import top.wecoding.core.exception.user.UnauthorizedException;
import top.wecoding.core.jwt.model.TokenInfo;
import top.wecoding.core.jwt.props.JwtProperties;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import static top.wecoding.core.constant.SecurityConstants.BASIC_HEADER_PREFIX;

/**
 * Jwt工具类
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Slf4j
public class JwtUtils {

    private static JwtProperties jwtProperties;

    public static JwtProperties getJwtProperties() {
        if (jwtProperties == null) {
            jwtProperties = SpringUtil.getBean(JwtProperties.class);
        }
        return jwtProperties;
    }

    /**
     * 签名加密
     */
    public static String getBase64Security() {
        return Base64.getEncoder().encodeToString(getJwtProperties().getSignKey().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 从请求头中获取客户端信息
     *
     * @param header 请求头
     * @return 客户端
     */
    @SneakyThrows
    public static String getClientIdByHeader(String header) {
        return extractClient(header)[0];
    }

    /**
     * 解析请求头中存储的 client 信息
     *
     * @param header 请求头信息
     * @return [0]: clientId, [1]: 密码
     */
    public static String[] extractClient(String header) {
        Assert.notNull(header, "请求头中 Client 信息为空!");
        Assert.isTrue(header.startsWith(BASIC_HEADER_PREFIX), "客户端信息不正确!");

        byte[] base64Token = header.substring(BASIC_HEADER_PREFIX.length()).getBytes(StandardCharsets.UTF_8);
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (Exception e) {
            throw new ArgumentException("客户端令牌解码失败!");
        }

        String token = new String(decoded, StandardCharsets.UTF_8);
        int delim = token.indexOf(StrPool.COLON);
        if (delim < 0) {
            throw new ArgumentException("无效客户端令牌!");
        }

        // [0]: clientId, [1]: 密码
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
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