package top.wecoding.core.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 是或否的枚举
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Getter
public enum YesOrNotEnum {

    /**
     * 是
     */
    Y("Y", "是"),

    /**
     * 否
     */
    N("N", "否");

    private static final Map<String, YesOrNotEnum> KEY_MAP = new HashMap<>();

    static {
        for (YesOrNotEnum item : YesOrNotEnum.values()) {
            KEY_MAP.put(item.getCode(), item);
        }
    }

    private final String code;

    private final String message;

    YesOrNotEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static YesOrNotEnum of(String value) {
        return KEY_MAP.get(value);
    }

}
