package top.wecoding.core.auth.util;

import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import top.wecoding.core.auth.model.LoginUser;
import top.wecoding.core.constant.TokenConstant;
import top.wecoding.core.enums.UserTypeEnum;
import top.wecoding.core.jwt.model.AuthInfo;
import top.wecoding.core.jwt.util.TokenUtil;
import top.wecoding.core.util.HttpServletUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 安全服务工具类
 *
 * @author liuyuhui
 * @date 2022/5/13
 * @qq 1515418211
 */
@Slf4j
@UtilityClass
public class AuthUtil {

    /**
     * 获取客户端 ID
     *
     * @return clientId
     */
    public String getClientId() {
        return getAuthInfo().getClientId();
    }

    /**
     * 获取登录用户账户
     *
     * @return 登录用户账户
     **/
    public String getAccount() {
        return getAuthInfo().getAccount();
    }

    /**
     * 获取登录用户 ID
     *
     * @return UserID
     */
    public Long getUserId() {
        return getAuthInfo().getUserId();
    }

    public AuthInfo getAuthInfo() {
        String token = getToken(HttpServletUtils.getRequest());
        return TokenUtil.getAuthInfo(token);
    }

    /**
     * 获取登录用户，获取不到抛出异常
     *
     * @return LoginUser
     */
    public LoginUser getLoginUser() {
        HttpServletRequest request = HttpServletUtils.getRequest();
        String token = getToken(request);
        AuthInfo authInfo = TokenUtil.getAuthInfo(token);
        return new LoginUser();
    }

    /**
     * 获取登录用户，获取不到抛出异常
     *
     * @return LoginUser
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        String token = getToken(request);
        AuthInfo authInfo = TokenUtil.getAuthInfo(token);


        return new LoginUser();
    }

    /**
     * 获取登录用户，获取不到不抛出异常
     *
     * @return LoginUser
     */
    public LoginUser getLoginUserWithoutException() {
        try {
            return getLoginUser();
        } catch (Exception e) {
            log.warn(" >>> 获取登录用户失败. Thread:{}", Thread.currentThread());
            return null;
        }
    }

    /**
     * 判断当前用户是否是管理员
     *
     * @return 结果
     */
    public boolean isAdmin() {
        return isUserType(UserTypeEnum.SUPER_ADMIN);
    }

    /**
     * 判断当前登录用户是否是指定用户类型
     *
     * @param userTypeEnum 用户类型
     * @return 结果
     */
    public boolean isUserType(UserTypeEnum userTypeEnum) {
        String userType = getLoginUser().getUserType();
        return userTypeEnum.eq(userType);
    }

    /**
     * 获取 Token
     *
     * @param request request
     * @return Token
     */
    public String getToken(HttpServletRequest request) {
        String auth = request.getHeader(TokenConstant.AUTHENTICATION);
        if (StrUtil.isNotBlank(auth)) {
            return auth;
        }
        return request.getParameter(TokenConstant.AUTHENTICATION);
    }

}
