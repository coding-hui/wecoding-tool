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
package top.wecoding.core.log.aspect;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import top.wecoding.core.log.annotation.Log;
import top.wecoding.core.log.publisher.LogPublisher;
import top.wecoding.core.util.HttpServletUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 日志切面处理
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Slf4j
@Aspect
public class LogAspect {

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(top.wecoding.core.log.annotation.Log)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     */
    @Around("logPointCut()")
    public Object doAfterReturning(ProceedingJoinPoint point) throws Throwable {

        HttpServletRequest request = HttpServletUtils.getRequest();
        if (ObjectUtil.isNull(request)) {
            return point.proceed();
        }

        // 获得注解
        Log controllerLog = getAnnotationLog(point);
        if (ObjectUtil.isNull(controllerLog)) {
            return point.proceed();
        }

        // 打印执行时间
        long startTime = System.nanoTime();
        // 计算耗时
        long endTime;
        Object result = null;
        Exception e = null;
        try {
            result = point.proceed();
        } catch (Exception ex) {
            e = ex;
            throw e;
        } finally {
            endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
            log.debug(" >>> 开始发送日志记录事件");
            if (e != null) {
                LogPublisher.publishExceptionEvent(controllerLog, endTime, point, e);
            } else {
                LogPublisher.publishOperationEvent(controllerLog, endTime, point, JSONObject.toJSONString(result));
            }
        }

        return result;
    }

    /**
     * 获取注解
     */
    private Log getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

}
