package top.wecoding.core.test.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 模拟登录用户
 *
 * @author liuyuhui
 * @date 2022/5/15
 * @qq 1515418211
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface WithMockLoginUser {

    /**
     * 用户名
     */
    String username() default "admin";

    /**
     * 密码
     */
    String password() default "123456";

}
