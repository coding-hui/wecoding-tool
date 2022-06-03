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
