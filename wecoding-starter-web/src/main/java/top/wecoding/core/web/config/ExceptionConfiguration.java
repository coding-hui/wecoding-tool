package top.wecoding.core.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.wecoding.core.exception.handler.AbstractGlobalExceptionHandler;

/**
 * 全局异常处理
 *
 * @author liuyuhui
 * @date 2022/04/04
 * @qq 1515418211
 */
@RestControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.ANY)
@Configuration(proxyBeanMethods = false)
public class ExceptionConfiguration extends AbstractGlobalExceptionHandler {
}
