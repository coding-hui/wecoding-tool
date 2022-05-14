package top.wecoding.core.log.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.wecoding.core.log.entity.SysLoginLog;
import top.wecoding.core.log.entity.SysOpLog;
import top.wecoding.core.model.response.Response;

import java.util.List;

/**
 * @author liuyuhui
 * @date 2021/09/07
 * @qq 1515418211
 */
@Slf4j
@Component
public class RemoteLogFallbackFactory implements RemoteLogService {

    @Override
    public Response saveOpLog(SysOpLog sysLog, String from) {
        return null;
    }

    @Override
    public Response saveLoginLog(SysLoginLog sysLoginLog, String from) {
        return null;
    }

    @Override
    public Response saveLogoutLog(List<String> userKeys, String from) {
        return null;
    }
}
