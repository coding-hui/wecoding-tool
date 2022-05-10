package top.wecoding.core.enums;

import lombok.Getter;

/**
 * 用户性别
 *
 * @author liuyuhui
 * @qq 1515418211
 * @date 2021/07/28
 */
@Getter
public enum GenderEnum {

    /**
     * 男
     */
    MAN("1", "男"),

    /**
     * 女
     */
    WOMAN("2", "女"),

    /**
     * 未知
     */
    NONE("3", "未知");

    private final String code;

    private final String message;

    GenderEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
