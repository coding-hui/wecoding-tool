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

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import top.wecoding.core.constant.SecurityConstants;
import top.wecoding.core.constant.ServiceNameConstants;
import top.wecoding.core.log.entity.SysLoginLog;
import top.wecoding.core.log.entity.SysOpLog;
import top.wecoding.core.model.response.Response;

import java.util.List;

/**
 * @author liuyuhui
 * @qq 1515418211
 */
@FeignClient(
        contextId = "remoteLogService",
        value = ServiceNameConstants.SYSTEM_SERVICE,
        fallbackFactory = RemoteLogFallbackFactory.class)
public interface RemoteLogService {

    String API_PREFIX = "/log";

    /**
     * 保存日志
     *
     * @param sysLog 日志实体
     * @param from   内部调用标志
     * @return 操作结果
     */
    @PostMapping(API_PREFIX)
    Response saveOpLog(@RequestBody SysOpLog sysLog, @RequestHeader(SecurityConstants.FROM_SOURCE) String from);

    /**
     * 保存登录日志
     *
     * @param sysLoginLog 日志实体
     * @param from        内部调用标志
     * @return 操作结果
     */
    @PostMapping("/login-log")
    Response saveLoginLog(@RequestBody SysLoginLog sysLoginLog, @RequestHeader(SecurityConstants.FROM_SOURCE) String from);

    /**
     * 保存登出日志
     *
     * @param userKeys 用户标识
     * @param from     内部调用标志
     * @return 操作结果
     */
    @PostMapping("/login-log/exit")
    Response saveLogoutLog(@RequestBody List<String> userKeys, @RequestHeader(SecurityConstants.FROM_SOURCE) String from);

}
