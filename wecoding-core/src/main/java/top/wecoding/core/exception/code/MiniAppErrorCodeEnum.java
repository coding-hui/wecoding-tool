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
package top.wecoding.core.exception.code;

import cn.hutool.core.util.StrUtil;
import top.wecoding.core.annotion.ErrorCodePrefix;
import top.wecoding.core.constant.ErrorCodeConstant;
import top.wecoding.core.exception.base.BaseErrorCode;
import top.wecoding.core.factory.ErrorCodeFactory;

/**
 * 小程序端异常
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@ErrorCodePrefix(source = ErrorCodeConstant.MINI_APP_ERROR)
public enum MiniAppErrorCodeEnum implements BaseErrorCode {

    /**
     * 小程序端 一级宏观错误码
     */
    OTHER_ERROR("0000", "系统错误，请稍候再试"),

    REGISTER_ERROR("0100", "用户注册错误"),

    LOGIN_ERROR("0200", "用户登录异常"),
    NOT_FOUND_ACCOUNT("0201", "用户账户不存在"),
    ACCOUNT_HAS_FREEZE("0202", "用户账户被冻结"),
    ACCOUNT_HAS_DELETED("0203", "用户账户已作废"),
    ACCOUNT_PWD_ERROR("0210", "账号或密码错误"),
    ACCOUNT_PWD_EMPTY("0211", "账号或密码为空"),
    ACCOUNT_AUTH_ERROR("0220", "用户身份校验失败"),
    LOGIN_EXPIRED("0230", "登录过期，请重新登录"),
    VALID_TOKEN_ERROR("0240", "请求 token 错误"),
    VALID_CODE_ERROR("0250", "用户验证码错误"),

    ACCESS_PERMISSION_ERROR("0300", "访问权限异常"),
    DEMO_EXP_ERROR("0310", "演示环境，无法操作"),
    NO_PERMISSION("0320", "没有权限访问资源，请联系管理员"),
    NO_PERMISSION_OPERATE("0321", "没有权限操作该数据，请联系管理员"),

    REQUEST_PARAMETER_ERROR("0400", "用户请求参数错误"),
    REQUEST_JSON_ERROR("0410", "请求JSON参数格式不正确，请检查参数格式"),
    REQUEST_TYPE_IS_JSON("0411", "参数传递格式不支持，请使用JSON格式传参"),
    REQUEST_METHOD_IS_POST("0420", "不支持该请求方法，请求方法应为POST"),
    REQUEST_METHOD_IS_GET("0421", "不支持该请求方法，请求方法应为GET"),

    REQUEST_SERVICE_ERROR("0500", "用户请求服务异常"),
    URL_NOT_EXIST("0510", "资源路径不存在，请检查请求地址"),

    USER_CURRENT_VERSION_ERROR("0700", "用户当前版本异常"),

    USER_PRIVACY_ERROR("0800", "用户隐私未授权"),

    USER_DEVICE_ERROR("0900", "用户设备异常");

    private final String errorCode;

    private String errorMessage;

    MiniAppErrorCodeEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorCode() {
        return ErrorCodeFactory.buildCode(MiniAppErrorCodeEnum.class, errorCode);
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public BaseErrorCode build(String errorMessage, Object... param) {
        this.errorMessage = StrUtil.format(errorMessage, param);
        return this;
    }

}
