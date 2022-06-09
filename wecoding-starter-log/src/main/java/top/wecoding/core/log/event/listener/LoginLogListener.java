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
package top.wecoding.core.log.event.listener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import top.wecoding.core.constant.SecurityConstants;
import top.wecoding.core.log.entity.SysLoginLog;
import top.wecoding.core.log.event.LoginLogEvent;
import top.wecoding.core.log.feign.RemoteLogService;

/**
 * @author liuyuhui
 * @qq 1515418211
 */
@Slf4j
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
