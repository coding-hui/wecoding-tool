package top.wecoding.core.mongo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * type表示查询类似，默认为equals
 * attribute表示要查询的属性，默认为空串，如果为空则为字段名称
 *
 * @author liuyuhui
 * @author LaoWang
 * @date 2022/02/14
 * @qq 1515418211
 * @link <a href="https://gitee.com/qwer.com/open-mongodb">...</a>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryField {

    QueryType type();

    String attribute() default "";

}
