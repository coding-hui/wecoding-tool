package top.wecoding.core.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyuhui
 * @qq 1515418211
 */
@Getter
public enum SystemConfigTypeEnum {

    /**
     * 系统配置类型
     */
    SYSTEM("system", "系统配置"),

    CUSTOMER("customer", "业务配置");

    private static final Map<String, SystemConfigTypeEnum> KEY_MAP = new HashMap<>();

    static {
        for (SystemConfigTypeEnum item : SystemConfigTypeEnum.values()) {
            KEY_MAP.put(item.getCode(), item);
        }
    }

    private final String code;

    private final String message;

    SystemConfigTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static SystemConfigTypeEnum of(String value) {
        return KEY_MAP.get(value);
    }

}
