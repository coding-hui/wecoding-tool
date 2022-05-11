package top.wecoding.core.log.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import top.wecoding.core.constant.SecurityConstants;
import top.wecoding.core.log.entity.SysLoginLog;
import top.wecoding.core.log.feign.RemoteLogService;

/**
 * @author liuyuhui
 * @qq 1515418211
 */
@Slf4j
@Component
@AllArgsConstructor
public class LoginLogListener {

    private final RemoteLogService remoteLogService;

    @Async
    @Order
    @EventListener(LoginLogEvent.class)
    public void saveSysLoginLog(LoginLogEvent event) {
        SysLoginLog sysLoginLog = event.getSysLoginLog();
        remoteLogService.saveLoginLog(sysLoginLog, SecurityConstants.INNER);
    }

}
