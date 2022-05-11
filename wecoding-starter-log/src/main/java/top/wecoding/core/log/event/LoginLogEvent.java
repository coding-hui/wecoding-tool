package top.wecoding.core.log.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import top.wecoding.core.log.entity.SysLoginLog;

/**
 * @author liuyuhui
 * @qq 1515418211
 */
@Getter
public class LoginLogEvent extends ApplicationEvent {

    private final SysLoginLog sysLoginLog;

    public LoginLogEvent(final SysLoginLog source) {
        super(source);
        this.sysLoginLog = source;
    }

}
