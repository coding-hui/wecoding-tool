package top.wecoding.core.log.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author liuyuhui
 * @date 2021/09/07
 * @qq 1515418211
 */
@Getter
@RequiredArgsConstructor
public enum LogTypeEnum {

    /**
     * 正常
     */
    NORMAL("1", "正常日志"),

    /**
     * 错误
     */
    ERROR("0", "异常日志");

    /**
     * 类型
     */
    private final String type;

    /**
     * 描述
     */
    private final String description;

}
