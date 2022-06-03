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
package top.wecoding.core.feign.interceptor;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.wecoding.core.constant.SecurityConstants;
import top.wecoding.core.constant.TokenConstant;
import top.wecoding.core.context.security.SecurityContextHolder;
import top.wecoding.core.util.HttpServletUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * feign 请求拦截器
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Slf4j
@Component
public class FeignAddHeaderRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.info(" >>> 进行远程调用, 请求路径={}, thread-id={}, name={}", requestTemplate.path(), Thread.currentThread().getId(), Thread.currentThread().getName());

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Map<String, Object> headers;
        if (requestAttributes == null) {
            headers = SecurityContextHolder.getLocalMap();
        } else {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            if (ObjectUtil.isNull(request)) {
                log.warn(" >>> 无法在远程调用时获取请求头中的参数，请求路径为:{}", requestTemplate.path());
                return;
            }

            headers = HttpServletUtils.getHeaders(request);
        }

        String userKey = URLUtil.encode(Convert.toStr(headers.get(SecurityConstants.USER_KEY)));
        if (StrUtil.isNotEmpty(userKey)) {
            requestTemplate.header(SecurityConstants.USER_KEY, userKey);
        }

        String userId = URLUtil.encode(Convert.toStr(headers.get(SecurityConstants.DETAILS_USER_ID)));
        if (StrUtil.isNotEmpty(userId)) {
            requestTemplate.header(SecurityConstants.DETAILS_USER_ID, userId);
        }

        String account = URLUtil.encode(Convert.toStr(headers.get(SecurityConstants.DETAILS_ACCOUNT)));
        if (StrUtil.isNotEmpty(account)) {
            requestTemplate.header(SecurityConstants.DETAILS_ACCOUNT, account);
        }

        String clientId = URLUtil.encode(Convert.toStr(headers.get(SecurityConstants.DETAILS_CLIENT_ID)));
        if (StrUtil.isNotEmpty(clientId)) {
            requestTemplate.header(SecurityConstants.DETAILS_CLIENT_ID, clientId);
        }

        String authentication = Convert.toStr(headers.get(TokenConstant.AUTHENTICATION));
        if (StrUtil.isNotEmpty(authentication)) {
            requestTemplate.header(TokenConstant.AUTHENTICATION, authentication);
        }
    }

}
