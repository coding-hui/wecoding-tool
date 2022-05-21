package top.wecoding.core.security.interceptor;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import top.wecoding.core.auth.util.AuthUtil;
import top.wecoding.core.security.util.TokenUtil;
import top.wecoding.core.constant.SecurityConstants;
import top.wecoding.core.context.security.SecurityContextHolder;
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
public class HeaderInterceptor implements AsyncHandlerInterceptor {

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
            Optional.ofNullable(TokenUtil.getLoginUser(token)).ifPresent(loginUser ->
                    SecurityContextHolder.set(SecurityConstants.LOGIN_USER, loginUser));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SecurityContextHolder.remove();
    }

}
