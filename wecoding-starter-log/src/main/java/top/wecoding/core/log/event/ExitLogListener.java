package top.wecoding.core.log.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import top.wecoding.core.constant.SecurityConstants;
import top.wecoding.core.log.feign.RemoteLogService;

import java.util.List;

/**
 * @author liuyuhui
 * @qq 1515418211
 */
@Slf4j
@AllArgsConstructor
public class ExitLogListener {

    private final RemoteLogService remoteLogService;

    @Async
    @Order
    @EventListener(ExitLogEvent.class)
    public void saveSysLoginLog(ExitLogEvent event) {
        List<String> userKeys = event.getUserKeys();
        remoteLogService.saveLogoutLog(userKeys, SecurityConstants.INNER);
    }

}
