package top.wecoding.core.enums;

import lombok.Getter;

/**
 * 访问日志类型枚举
 *
 * @author liuyuhui
 * @date 2022/4/30
 * @qq 1515418211
 */
@Getter
public enum VisLogTypeEnum {

    /**
     * 登录日志
     */
    LOGIN("0", "登录成功"),

    /**
     * 登录日志
     */
    LOGIN_ERROR("1", "登录失败"),


    /**
     * 退出日志
     */
    EXIT("2", "登出");

    private final String code;

    private final String message;

    VisLogTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
