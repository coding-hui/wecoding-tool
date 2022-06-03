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
package top.wecoding.core.security.aspect;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import top.wecoding.core.constant.SecurityConstants;
import top.wecoding.core.exception.user.InnerAuthException;
import top.wecoding.core.security.annotation.InnerAuth;
import top.wecoding.core.util.HttpServletUtils;

/**
 * 内部服务调用验证处理切面
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Slf4j
@Aspect
public class InnerAuthAspect implements Ordered {

    @Around("@within(innerAuth) || @annotation(innerAuth)")
    public Object innerAround(ProceedingJoinPoint point, InnerAuth innerAuth) throws Throwable {
        String source = HttpServletUtils.getRequest().getHeader(SecurityConstants.FROM_SOURCE);

        // 是否是内部请求
        if (!StrUtil.equals(SecurityConstants.INNER, source)) {
            log.warn("访问接口 {} 没有权限", point.getSignature().getName());
            throw new InnerAuthException("没有内部访问权限，不允许访问");
        }

        // 用户验证
        String userId = HttpServletUtils.getRequest().getHeader(SecurityConstants.DETAILS_USER_ID);
        String account = HttpServletUtils.getRequest().getHeader(SecurityConstants.DETAILS_ACCOUNT);
        if (innerAuth.value() && StrUtil.hasBlank(userId, account)) {
            log.warn("访问接口 {} 没有权限", point.getSignature().getName());
            throw new InnerAuthException("没有设置用户信息，不允许访问");
        }

        log.debug(" >>> 内部应用访问，不检验权限，访问接口: {}", point.getSignature().getName());
        return point.proceed();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

}
