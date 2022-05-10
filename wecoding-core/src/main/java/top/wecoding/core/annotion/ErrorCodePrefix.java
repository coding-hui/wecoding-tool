package top.wecoding.core.annotion;

import java.lang.annotation.*;

/**
 * 标识在异常枚举类上，用来标识类级别异常枚举编码
 * <p>
 * 错误码为字符串类型，分成三个部分：
 * 错误应用平台(可选) - 错误产生来源(一位) + 四位数字编号
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ErrorCodePrefix {

    /**
     * 错误应用平台
     */
    String app() default "";

    /**
     * 错误产生来源
     */
    String source();

}
