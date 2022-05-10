package top.wecoding.core.enums;

import lombok.Getter;

/**
 * @author liuyuhui
 * @qq 1515418211
 */
@Getter
public enum UserStatusEnum {

    /**
     * 用户状态
     */
    OK("0", "正常"),

    DISABLE("1", "停用"),

    DELETED("2", "删除");

    private final String code;

    private final String info;

    UserStatusEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

}
