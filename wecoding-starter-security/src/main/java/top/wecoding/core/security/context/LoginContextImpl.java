package top.wecoding.core.security.context;

import lombok.extern.slf4j.Slf4j;
import top.wecoding.core.context.login.LoginContext;
import top.wecoding.core.context.security.SecurityContextHolder;
import top.wecoding.core.enums.UserTypeEnum;
import top.wecoding.core.model.LoginUser;
import top.wecoding.core.security.util.SecurityUtils;

/**
 * 登录用户上下文接口实现
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Slf4j
public class LoginContextImpl implements LoginContext {

    @Override
    public String getUserCacheKey() {
        return SecurityContextHolder.getUserKey();
    }

    @Override
    public String getClientId() {
        return SecurityContextHolder.getClientId();
    }

    @Override
    public String getAccount() {
        return SecurityContextHolder.getAccount();
    }

    @Override
    public Long getUserId() {
        return SecurityContextHolder.getUserId();
    }

    @Override
    public LoginUser getLoginUser() {
        return SecurityUtils.getLoginUser();
    }

    @Override
    public LoginUser getLoginUserWithoutException() {
        try {
            return this.getLoginUser();
        } catch (Exception e) {
            log.info(" >>> 获取用户失败, 具体信息为: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public boolean isAdmin() {
        return this.isUserType(UserTypeEnum.SUPER_ADMIN);
    }

    @Override
    public boolean isUserType(UserTypeEnum userTypeEnum) {
        String userType = this.getLoginUser().getUserType();
        return userTypeEnum.eq(userType);
    }

}