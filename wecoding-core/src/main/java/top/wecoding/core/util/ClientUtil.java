package top.wecoding.core.util;

import cn.hutool.core.codec.Base64;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import top.wecoding.core.constant.StrPool;
import top.wecoding.core.exception.ArgumentException;
import top.wecoding.core.exception.Assert;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

import static top.wecoding.core.constant.SecurityConstants.BASIC_HEADER_PREFIX;

/**
 * 客户端工具类
 *
 * @author liuyuhui
 * @qq 1515418211
 */
public class ClientUtil {

    @SneakyThrows
    public static String getClientId(ServerHttpRequest request) {
        String header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return determineClient(header)[0];
    }

    @SneakyThrows
    public static String getClientId() {
        if (determineClient().length == 2) {
            return determineClient()[0];
        }
        return null;
    }

    public static String[] determineClient() {
        HttpServletRequest request = HttpServletUtils.getRequest();
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        return determineClient(header);
    }

    public static String[] determineClient(String header) {
        Assert.notNull(header, "请求头中 Client 信息为空!");
        Assert.isTrue(header.startsWith(BASIC_HEADER_PREFIX), "客户端信息不正确!");

        byte[] base64Token = header.substring(BASIC_HEADER_PREFIX.length()).getBytes(StandardCharsets.UTF_8);
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
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

}
