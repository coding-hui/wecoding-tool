package top.wecoding.core.security.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import top.wecoding.core.constant.StrPool;
import top.wecoding.core.constant.TokenConstant;
import top.wecoding.core.exception.code.ClientErrorCodeEnum;
import top.wecoding.core.exception.user.UnauthorizedException;
import top.wecoding.core.model.LoginUser;
import top.wecoding.core.security.service.TokenService;
import top.wecoding.core.util.HttpServletUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限工具类
 *
 * @author liuyuhui
 * @date 2022
 * @qq 1515418211
 */
public class SecurityUtils {

    private static final TokenService tokenService;

    static {
        tokenService = SpringUtil.getBean(TokenService.class);
    }

    /**
     * 获取当前用户缓存信息, 如果未登录，则抛出异常
     *
     * @return 用户缓存信息
     */
    public static LoginUser getLoginUser() {
        String token = getToken();
        if (token == null) {
            throw new UnauthorizedException(ClientErrorCodeEnum.VALID_TOKEN_ERROR);
        }
        LoginUser loginUser = getLoginUser(token);
        if (loginUser == null) {
            throw new UnauthorizedException(ClientErrorCodeEnum.VALID_TOKEN_ERROR);
        }
        return loginUser;
    }

    /**
     * 获取当前登录用户信息
     */
    public static LoginUser getLoginUser(String token) {
        return tokenService.getLoginUser(token);
    }

    /**
     * 获取请求token
     */
    public static String getToken() {
        return getToken(HttpServletUtils.getRequest());
    }

    /**
     * 根据request获取请求token
     */
    public static String getToken(HttpServletRequest request) {
        // 从header获取token标识
        String token = request.getHeader(TokenConstant.AUTHENTICATION);
        return replaceTokenPrefix(token);
    }

    /**
     * 去掉token前缀
     */
    public static String replaceTokenPrefix(String token) {
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StrUtil.isNotEmpty(token) && token.startsWith(TokenConstant.PREFIX)) {
            token = token.replaceFirst(TokenConstant.PREFIX, StrPool.EMPTY);
        }
        return token;
    }

}
