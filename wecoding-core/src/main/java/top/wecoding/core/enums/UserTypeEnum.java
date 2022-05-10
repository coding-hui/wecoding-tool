package top.wecoding.core.enums;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户类型枚举
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Getter
public enum UserTypeEnum {

    SUPER_ADMIN("1", "管理员"),

    TEACHER("2", "教师"),

    ASSISTANT("3", "助教"),

    STUDENT("5", "学生"),

    COMMON("6", "普通用户");

    private static final Map<String, UserTypeEnum> KEY_MAP = new HashMap<>();

    static {
        for (UserTypeEnum item : UserTypeEnum.values()) {
            KEY_MAP.put(item.getCode(), item);
        }
    }

    private final String code;
    private final String message;

    UserTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static UserTypeEnum of(String userType) {
        return KEY_MAP.get(userType);
    }

    public boolean eq(String userType) {
        UserTypeEnum userTypeEnum = of(userType);
        if (ObjectUtil.isNotNull(userTypeEnum)) {
            return this.code.equals(userTypeEnum.getCode());
        }
        return false;
    }

}
