package top.wecoding.core.log.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @author liuyuhui
 * @qq 1515418211
 */
@Getter
public class ExitLogEvent extends ApplicationEvent {

    private final List<String> userKeys;

    public ExitLogEvent(final List<String> userKeys) {
        super(userKeys);
        this.userKeys = userKeys;
    }

}
