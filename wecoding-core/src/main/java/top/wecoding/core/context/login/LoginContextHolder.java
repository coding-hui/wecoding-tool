package top.wecoding.core.context.login;

import cn.hutool.extra.spring.SpringUtil;

/**
 * 获取当前登录用户信息上下文
 *
 * @author liuyuhui
 * @qq 1515418211
 */
public class LoginContextHolder {

    public static LoginContext me() {
        return SpringUtil.getBean(LoginContext.class);
    }

}
