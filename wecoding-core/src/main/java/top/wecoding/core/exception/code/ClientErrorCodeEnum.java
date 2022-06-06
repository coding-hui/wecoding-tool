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
 * 用户端异常
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@ErrorCodePrefix(source = ErrorCodeConstant.CLIENT_ERROR)
public enum ClientErrorCodeEnum implements BaseErrorCode {

    /**
     * 用户端错误 一级宏观错误码
     */
    CLIENT_ERROR("0001", "用户端错误"),

    REGISTER_ERROR("0100", "用户注册错误"),

    LOGIN_ERROR("0200", "用户登录异常"),
    NOT_FOUND_ACCOUNT("0201", "用户账户不存在"),
    ACCOUNT_HAS_FREEZE("0202", "用户账户被冻结"),
    ACCOUNT_HAS_DELETED("0203", "用户账户已作废"),

    ACCOUNT_AUTH_ERROR("0210", "用户身份校验失败"),
    VALID_CODE_ERROR("0211", "用户验证码错误"),
    EMPTY_CODE_ERROR("0212", "用户验证码为空"),
    EXPIRED_CODE_ERROR("0213", "验证码错误已过期"),
    ACCOUNT_PWD_EMPTY("0214", "账号或密码为空"),
    ACCOUNT_PWD_ERROR("0214", "账号或密码错误"),
    LOGIN_EXPIRED("0215", "登录过期，请重新登录"),
    VALID_TOKEN_ERROR("0220", "请求令牌错误"),
    JWT_TOKEN_ERROR("", "无效令牌"),
    JWT_TOKEN_EXPIRED("0221", "身份令牌已过期，请重新登录"),
    JWT_SIGNATURE_ERROR("0222", "非法身份令牌，无效签名"),
    JWT_TOKEN_IS_EMPTY("0223", "身份令牌为空"),
    JWT_PARSE_TOKEN_ERROR("0224", "解析身份令牌错误"),
    NO_LOGIN_USER("0230", "未登录"),

    ACCESS_PERMISSION_ERROR("0300", "访问权限异常"),
    DEMO_EXP_ERROR("0310", "演示环境，无法操作"),
    NO_PERMISSION("0320", "没有权限访问资源，请联系管理员"),
    NO_PERMISSION_OPERATE("0330", "没有权限操作该数据，请联系管理员"),
    TOKEN_GRANT_TYPE_ERROR("0340", "不支持该登录方式"),
    INNER_AUTH_ERROR("0350", "内部认证异常"),

    REQUEST_PARAMETER_ERROR("0400", "用户请求参数错误"),
    REQUEST_JSON_ERROR("0410", "请求JSON参数格式不正确，请检查参数格式"),
    REQUEST_TYPE_IS_JSON("0411", "参数传递格式不支持，请使用JSON格式传参"),
    REQUEST_METHOD_IS_POST("0420", "不支持该请求方法，请求方法应为POST"),
    REQUEST_METHOD_IS_GET("0421", "不支持该请求方法，请求方法应为GET"),
    REQUEST_METHOD_IS_DELETE("0422", "不支持该请求方法，请求方法应为DELETE"),
    REQUEST_METHOD_IS_PUT("0423", "不支持该请求方法，请求方法应为PUT"),
    REQUEST_METHOD_NOT_ALLOWED("0424", "不支持该请求方法"),
    REQUIRED_FILE_PARAM("0426", "请求中必须至少包含一个有效文件"),
    NOT_WRITE_STATUS("0427", "请求状态值不合法"),
    TOO_MANY_REQUESTS("0423", "请求过多，请稍后重试"),

    REQUEST_SERVICE_ERROR("0500", "用户请求服务异常"),
    URL_NOT_EXIST("0510", "资源路径不存在，请检查请求地址"),
    FORBIDDEN_ERROR("0510", "资源路径不存在，请检查请求地址"),
    DATA_NOT_FOUNT("0520", "找不到对应资源"),

    USER_UPLOAD_ERROR("0600", "上传文件异常"),

    USER_CURRENT_VERSION_ERROR("0700", "用户当前版本异常"),

    USER_PRIVACY_ERROR("0800", "用户隐私未授权"),

    USER_DEVICE_ERROR("0900", "用户设备异常"),

    LOGOUT_ERROR("1000", "退出异常"),
    LOGOUT_TOKEN_IS_BLANK("1010", "退出失败，请求令牌为空"),
    LOGOUT_TOKEN_IS_ERROR("1020", "退出失败，请求令牌失效");

    private final String errorCode;

    private String errorMessage;

    ClientErrorCodeEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorCode() {
        return ErrorCodeFactory.buildCode(ClientErrorCodeEnum.class, errorCode);
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
