package top.wecoding.core.log.annotation;

import top.wecoding.core.log.enums.BusinessTypeEnum;

import java.lang.annotation.*;

/**
 * 操作日志记录注解
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    String value() default "";

    BusinessTypeEnum businessType() default BusinessTypeEnum.OTHER;

}
