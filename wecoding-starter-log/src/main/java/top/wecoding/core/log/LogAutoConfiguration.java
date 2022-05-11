package top.wecoding.core.log;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import top.wecoding.core.log.aspect.LogAspect;
import top.wecoding.core.log.event.ExitLogListener;
import top.wecoding.core.log.event.LoginLogListener;
import top.wecoding.core.log.event.OperationLogListener;
import top.wecoding.core.log.feign.RemoteLogService;

/**
 * @author liuyuhui
 * @date 2021/09/07
 * @qq 1515418211
 */
@EnableAsync
@AllArgsConstructor
@ConditionalOnWebApplication
@Configuration(proxyBeanMethods = false)
public class LogAutoConfiguration {

    private final RemoteLogService remoteLogService;

    @Bean
    public OperationLogListener operationLogListener() {
        return new OperationLogListener(remoteLogService);
    }

    @Bean
    public LoginLogListener loginLogListener() {
        return new LoginLogListener(remoteLogService);
    }

    @Bean
    public ExitLogListener exitLogListener() {
        return new ExitLogListener(remoteLogService);
    }

    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }

}
