package top.wecoding.core.log.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
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
public class RemoteLogFallbackFactory implements FallbackFactory<RemoteLogService> {

    @Override
    public RemoteLogService create(Throwable cause) {

        log.error(" >>> 日志服务调用失败，具体信息为:{}", cause.getMessage());

        return new RemoteLogService() {

            @Override
            public Response saveOpLog(SysOpLog sysLog, String from) {
                return Response.buildFailure(cause.getMessage());
            }

            @Override
            public Response saveLoginLog(SysLoginLog sysLoginLog, String from) {
                return Response.buildFailure(cause.getMessage());
            }

            @Override
            public Response saveLogoutLog(List<String> userKeys, String from) {
                return Response.buildFailure(cause.getMessage());
            }

        };
    }

}
