package top.wecoding.core.exception.code;

import cn.hutool.core.util.StrUtil;
import top.wecoding.core.annotion.ErrorCodePrefix;
import top.wecoding.core.constant.ErrorCodeConstant;
import top.wecoding.core.exception.base.BaseErrorCode;
import top.wecoding.core.factory.ErrorCodeFactory;

/**
 * @author liuyuhui
 * @qq 1515418211
 */
@ErrorCodePrefix(source = ErrorCodeConstant.REMOTE_ERROR)
public enum RemoteErrorCodeEnum implements BaseErrorCode {

    /**
     * 远程服务错误 一级宏观错误码
     */
    REMOTE_ERROR("0001", "请求远程服务发生错误"),

    /**
     * 远程服务错误 二级宏观错误码
     */
    SYSTEM_EXECUTION_TIMEOUT("0100", "请求超时，请稍后再试");

    private final String errorCode;

    private String errorMessage;

    RemoteErrorCodeEnum(String errorCode, String errorMessage) {
        this.errorCode = ErrorCodeFactory.buildCode(RemoteErrorCodeEnum.class, errorCode);
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorCode() {
        return ErrorCodeFactory.buildCode(RemoteErrorCodeEnum.class, errorCode);
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
