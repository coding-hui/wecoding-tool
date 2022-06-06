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
package top.wecoding.core.security.interceptor;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import top.wecoding.core.auth.util.AuthUtil;
import top.wecoding.core.constant.SecurityConstants;
import top.wecoding.core.context.security.SecurityContextHolder;
import top.wecoding.core.security.service.TokenService;
import top.wecoding.core.util.HttpServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static top.wecoding.core.constant.SecurityConstants.*;

/**
 * 自定义请求头拦截器，将Header数据封装到线程变量中方便获取
 *
 * @author liuyuhui
 * @date 2022
 * @qq 1515418211
 */
@Slf4j
@AllArgsConstructor
public class HeaderInterceptor implements AsyncHandlerInterceptor {

    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        SecurityContextHolder.setUserKey(HttpServletUtils.getHeader(request, USER_KEY));
        SecurityContextHolder.setUserId(HttpServletUtils.getHeader(request, DETAILS_USER_ID));
        SecurityContextHolder.setAccount(HttpServletUtils.getHeader(request, DETAILS_ACCOUNT));
        SecurityContextHolder.setClientId(HttpServletUtils.getHeader(request, DETAILS_CLIENT_ID));

        String token = AuthUtil.getToken(request);
        if (StrUtil.isNotBlank(token)) {
            Optional.ofNullable(tokenService.getLoginUser(token)).ifPresent(loginUser -> {
                SecurityContextHolder.set(SecurityConstants.LOGIN_USER, loginUser);
                SecurityContextHolder.setUserId(loginUser.getUserId());
                SecurityContextHolder.setUserKey(loginUser.getUuid());
                SecurityContextHolder.setAccount(loginUser.getAccount());
                SecurityContextHolder.setClientId(loginUser.getClientId());
            });
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SecurityContextHolder.remove();
    }

}
