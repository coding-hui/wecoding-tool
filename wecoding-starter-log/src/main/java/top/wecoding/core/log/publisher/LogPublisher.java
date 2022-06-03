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
package top.wecoding.core.log.publisher;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.http.HttpHeaders;
import top.wecoding.core.auth.model.LoginUser;
import top.wecoding.core.auth.util.AuthUtil;
import top.wecoding.core.constant.StrPool;
import top.wecoding.core.enums.VisLogTypeEnum;
import top.wecoding.core.exception.BizException;
import top.wecoding.core.exception.code.ClientErrorCodeEnum;
import top.wecoding.core.log.annotation.Log;
import top.wecoding.core.log.entity.SysLoginLog;
import top.wecoding.core.log.entity.SysOpLog;
import top.wecoding.core.log.enums.LogTypeEnum;
import top.wecoding.core.log.event.ExitLogEvent;
import top.wecoding.core.log.event.LoginLogEvent;
import top.wecoding.core.log.event.OperationLogEvent;
import top.wecoding.core.util.HttpServletUtils;
import top.wecoding.core.util.IpAddressUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 日志信息事件发送
 *
 * @author liuyuhui
 * @date 2022/4/29
 * @qq 1515418211
 */
@Slf4j
public class LogPublisher {

    /**
     * 登出日志
     *
     * @param userKeys 用户标识集合
     */
    public static void publishExitEvent(final List<String> userKeys) {
        SpringUtil.publishEvent(new ExitLogEvent(userKeys));
    }

    /**
     * 登录失败日志
     *
     * @param account     登录账号
     * @param failMessage 登录信息
     */
    public static void publishLoginFailEvent(final String account, final String failMessage) {
        SysLoginLog sysLoginLog = genRequestSysLoginLog();
        sysLoginLog.setAccount(account)
                .setStatus(VisLogTypeEnum.LOGIN_ERROR.getCode())
                .setMsg(failMessage);
        SpringUtil.publishEvent(new LoginLogEvent(sysLoginLog));
    }

    /**
     * 登录日志
     *
     * @param loginUser 登录账号
     */
    public static void publishLoginEvent(final LoginUser loginUser) {
        SysLoginLog sysLoginLog = genRequestSysLoginLog();
        sysLoginLog.setAccount(loginUser.getAccount())
                .setClientId(loginUser.getClientId())
                .setUserId(loginUser.getUserId())
                .setLoginUuid(loginUser.getUuid())
                .setStatus(VisLogTypeEnum.LOGIN.getCode())
                .setMsg(VisLogTypeEnum.LOGIN.getMessage());
        SpringUtil.publishEvent(new LoginLogEvent(sysLoginLog));
    }

    /**
     * 操作日志
     */
    public static void publishOperationEvent(Log log, long time, JoinPoint joinPoint, final String result) {
        SysOpLog opLog = genRequestSysOpLog();
        opLog.setTime(time)
                .setType(LogTypeEnum.NORMAL.getType())
                .setParams(getRequestParams(joinPoint))
                .setMethod(getClassName(joinPoint))
                .setBusinessType(log.businessType().ordinal())
                .setException(result)
                .setTitle(log.value());
        SpringUtil.publishEvent(new OperationLogEvent(opLog));
    }

    /**
     * 异常日志
     */
    public static void publishExceptionEvent(Log log, long time, JoinPoint joinPoint, Exception e) {
        SysOpLog opLog = genRequestSysOpLog();
        opLog.setTime(time)
                .setType(VisLogTypeEnum.LOGIN_ERROR.getCode())
                .setParams(getRequestParams(joinPoint))
                .setMethod(getClassName(joinPoint))
                .setBusinessType(log.businessType().ordinal())
                .setException(Arrays.toString(e.getStackTrace()))
                .setTitle(log.value());
        SpringUtil.publishEvent(new OperationLogEvent(opLog));
    }

    /**
     * 构建基础登录日志
     */
    private static SysLoginLog genRequestSysLoginLog() {
        HttpServletRequest request = HttpServletUtils.getRequest();
        if (ObjectUtil.isNotNull(request)) {
            UserAgent userAgent = UserAgentUtil.parse(request.getHeader(HttpHeaders.USER_AGENT));
            return SysLoginLog.builder()
                    .account(AuthUtil.getAccount())
                    .userId(AuthUtil.getUserId())
                    .ipaddr(IpAddressUtil.getIp(request))
                    .loginLocation(IpAddressUtil.getAddress(request))
                    .browser(userAgent.getBrowser().getName())
                    .os(userAgent.getOs().getName())
                    .loginTime(new Date())
                    .build();
        } else {
            throw new BizException(ClientErrorCodeEnum.REQUEST_PARAMETER_ERROR);
        }
    }

    /**
     * 构建基础操作日志
     */
    private static SysOpLog genRequestSysOpLog() {
        HttpServletRequest request = HttpServletUtils.getRequest();
        if (ObjectUtil.isNotNull(request)) {
            SysOpLog sysLog = new SysOpLog();
            UserAgent userAgent = UserAgentUtil.parse(request.getHeader(HttpHeaders.USER_AGENT));
            sysLog.setRequestMethod(request.getMethod())
                    .setRequestUri(URLUtil.getPath(request.getRequestURI()))
                    .setIp(IpAddressUtil.getIp(request))
                    .setUserAgent(userAgent.getBrowser() + StrPool.LEFT_DIVIDE + userAgent.getOs())
                    .setServiceId(AuthUtil.getClientId())
                    .setCreateUser(AuthUtil.getAccount())
                    .setLocation(IpAddressUtil.getAddress(request))
                    .setType(LogTypeEnum.NORMAL.getType())
                    .setCreateTime(new Date());
            return sysLog;
        } else {
            throw new BizException(ClientErrorCodeEnum.REQUEST_PARAMETER_ERROR);
        }
    }

    /**
     * 获取方法名称
     */
    private static String getClassName(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        return className + StrPool.PERIOD + methodName
                + StrPool.LEFT_ROUND_BRACKETS + StrPool.RIGHT_ROUND_BRACKETS;
    }

    /**
     * 获取请求参数
     */
    private static String getRequestParams(JoinPoint point) {
        Object[] args = point.getArgs();
        String params = "";
        try {
            params = JSONObject.toJSONString(args);
        } catch (Exception e) {
            try {
                params = Arrays.toString(args);
            } catch (Exception ex) {
                log.warn(" >>> 日志记录，解析参数异常，具体信息为：{}", ex.getLocalizedMessage());
            }
        }
        return params.trim();
    }

}
