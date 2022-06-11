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

    /**
     * 用户注册异常 二级宏观错误码
     */
    REGISTER_ERROR("0100", "用户注册错误"),

    /**
     * 用户登录异常 二级宏观错误码
     */
    LOGIN_ERROR("0200", "用户登录异常"),
    NOT_FOUND_ACCOUNT("0201", "用户账户不存在"),
    ACCOUNT_HAS_FREEZE("0202", "用户账户被冻结"),
    ACCOUNT_HAS_DELETED("0203", "用户账户已作废"),
    ACCOUNT_AUTH_ERROR("0210", "用户身份校验失败"),
    ACCOUNT_PWD_EMPTY("0211", "账号或密码为空"),
    ACCOUNT_PWD_ERROR("0212", "账号或密码错误"),
    LOGIN_EXPIRED("0220", "登录过期，请重新登录"),
    VALID_CODE_ERROR("0230", "用户验证码错误"),
    EMPTY_CODE_ERROR("0231", "用户验证码为空"),
    EXPIRED_CODE_ERROR("0232", "验证码错误已过期"),
    TOKEN_GRANT_TYPE_ERROR("0240", "不支持该登录方式"),

    /**
     * 访问权限异常 二级宏观错误码
     */
    ACCESS_PERMISSION_ERROR("0300", "访问权限异常"),
    NO_PERMISSION("0301", "没有权限访问资源，请联系管理员"),
    NO_PERMISSION_OPERATE("0302", "没有权限操作该数据，请联系管理员"),
    DEMO_EXP_ERROR("0310", "演示环境，无法操作"),
    INNER_AUTH_ERROR("0320", "内部认证异常"),
    NO_LOGIN_USER("0330", "无登录用户信息"),
    VALID_TOKEN_ERROR("0340", "请求令牌错误"),
    JWT_TOKEN_EXPIRED("0341", "身份令牌已过期，请重新登录"),
    JWT_SIGNATURE_ERROR("0342", "非法身份令牌，无效签名"),
    JWT_TOKEN_IS_EMPTY("0343", "身份令牌为空"),
    JWT_PARSE_TOKEN_ERROR("0344", "解析身份令牌错误"),


    /**
     * 用户请求参数异常 二级宏观错误码
     */
    REQUEST_PARAMETER_ERROR("0400", "用户请求参数错误"),
    REQUEST_JSON_ERROR("0410", "请求JSON参数格式不正确，请检查参数格式"),
    REQUEST_TYPE_IS_JSON("0411", "参数传递格式不支持，请使用JSON格式传参"),
    REQUEST_METHOD_IS_POST("0420", "不支持该请求方法，请求方法应为POST"),
    REQUEST_METHOD_IS_GET("0421", "不支持该请求方法，请求方法应为GET"),
    REQUEST_METHOD_IS_DELETE("0422", "不支持该请求方法，请求方法应为DELETE"),
    REQUEST_METHOD_IS_PUT("0423", "不支持该请求方法，请求方法应为PUT"),
    REQUEST_METHOD_NOT_ALLOWED("0424", "不支持该请求方法"),
    NOT_WRITE_STATUS("0430", "请求状态值不合法"),
    TOO_MANY_REQUESTS("0440", "请求太频繁啦，请稍后重试"),

    /**
     * 用户请求服务异常 二级宏观错误码
     */
    REQUEST_SERVICE_ERROR("0500", "用户请求服务异常"),
    URL_NOT_EXIST("0501", "资源路径不存在，请检查请求地址"),
    FORBIDDEN_ERROR("0502", "资源路径不存在，请检查请求地址"),
    DATA_NOT_FOUNT("0503", "找不到对应资源"),

    /**
     * 用户操作文件异常 二级宏观错误码
     */
    FILE_UPLOAD_ERROR("0600", "文件上传错误"),
    REQUIRED_FILE_PARAM("0601", "请求中必须至少包含一个有效文件"),
    FILE_TYPE_NOT_ALLOWED("0620", "不可上传该类型文件"),
    FILE_DOWNLOAD_ERROR("0630", "文件下载错误");

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
