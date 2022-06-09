/*
 * Copyright (c) 2022. WeCoding (wecoding@yeah.net).
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/lgpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.wecoding.core.log.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
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
@ConditionalOnClass(FallbackFactory.class)
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
