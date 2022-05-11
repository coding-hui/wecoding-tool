package top.wecoding.core.log.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import top.wecoding.core.log.entity.SysOpLog;

/**
 * @author liuyuhui
 * @qq 1515418211
 */
@Getter
public class OperationLogEvent extends ApplicationEvent {

    private final SysOpLog sysOpLog;

    public OperationLogEvent(final SysOpLog source) {
        super(source);
        this.sysOpLog = source;
    }

}
