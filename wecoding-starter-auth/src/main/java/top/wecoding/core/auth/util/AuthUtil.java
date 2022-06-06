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
package top.wecoding.core.auth.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import top.wecoding.core.auth.model.LoginUser;
import top.wecoding.core.constant.SecurityConstants;
import top.wecoding.core.constant.StrPool;
import top.wecoding.core.constant.TokenConstant;
import top.wecoding.core.context.security.SecurityContextHolder;
import top.wecoding.core.enums.UserTypeEnum;
import top.wecoding.core.exception.code.ClientErrorCodeEnum;
import top.wecoding.core.exception.user.UnauthorizedException;
import top.wecoding.core.util.HttpServletUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 安全服务工具类
 *
 * @author liuyuhui
 * @date 2022/5/13
 * @qq 1515418211
 */
@Slf4j
@UtilityClass
public class AuthUtil {

    /**
     * 获取客户端 ID
     *
     * @return clientId
     */
    public String getClientId() {
        return SecurityContextHolder.getClientId();
    }

    /**
     * 获取登录用户账户
     *
     * @return 登录用户账户
     **/
    public String getAccount() {
        return SecurityContextHolder.getAccount();
    }

    /**
     * 获取登录用户 ID
     *
     * @return UserID
     */
    public Long getUserId() {
        return SecurityContextHolder.getUserId();
    }

    /**
     * 获取登录用户，获取不到抛出异常
     *
     * @return LoginUser
     */
    public LoginUser getLoginUser() {
        return SecurityContextHolder.get(SecurityConstants.LOGIN_USER, LoginUser.class);
    }

    /**
     * 判断当前用户是否是管理员
     *
     * @return 结果
     */
    public boolean isAdmin() {
        return isUserType(UserTypeEnum.SUPER_ADMIN);
    }

    /**
     * 判断当前登录用户是否是指定用户类型
     *
     * @param userTypeEnum 用户类型
     * @return 结果
     */
    public boolean isUserType(UserTypeEnum userTypeEnum) {
        String userType = getLoginUser().getUserType();
        return userTypeEnum.eq(userType);
    }

    public String getToken() {
        HttpServletRequest request = HttpServletUtils.getRequest();
        if (ObjectUtil.isNull(request)) {
            throw new UnauthorizedException(ClientErrorCodeEnum.VALID_TOKEN_ERROR);
        }
        return getToken(request);
    }

    /**
     * 获取 Token
     *
     * @param request request
     * @return Token
     */
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(TokenConstant.AUTHENTICATION);
        if (StrUtil.isBlank(token)) {
            token = request.getParameter(TokenConstant.AUTHENTICATION);
        }
        return replaceTokenPrefix(token);
    }

    /**
     * 去掉 Token 前缀
     */
    public String replaceTokenPrefix(String token) {
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StrUtil.isNotEmpty(token) && token.startsWith(TokenConstant.PREFIX)) {
            token = token.replaceFirst(TokenConstant.PREFIX, StrPool.EMPTY);
        }
        return token;
    }

}
