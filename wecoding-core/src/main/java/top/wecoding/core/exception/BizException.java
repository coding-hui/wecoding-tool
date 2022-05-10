package top.wecoding.core.exception;

import top.wecoding.core.exception.base.BaseErrorCode;
import top.wecoding.core.exception.base.BaseUncheckedException;
import top.wecoding.core.exception.code.SystemErrorCodeEnum;

/**
 * 业务异常
 *
 * @author liuyuhui
 * @date 2021/07/31
 * @qq 1515418211
 */
public class BizException extends BaseUncheckedException {

    private static final long serialVersionUID = 1L;

    public BizException(String errorMessage) {
        super(errorMessage);
    }

    public BizException(BaseErrorCode errorCode) {
        super(errorCode);
    }

    public BizException(BaseErrorCode errorCode, Throwable e) {
        super(errorCode, e);
    }

    public BizException(BaseErrorCode errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public BizException(BaseErrorCode errorCode, String errorMessage, Throwable e) {
        super(errorCode, errorMessage, e);
    }

    public BizException(BaseErrorCode errorCode, String format, Object... args) {
        super(errorCode, format, args);
    }

    public static BizException wrap(BaseErrorCode baseErrorCode) {
        return new BizException(baseErrorCode);
    }

    public static BizException wrap(BaseErrorCode errorCode, Throwable cause) {
        return new BizException(errorCode, cause);
    }

    public static BizException wrap(String message, Object... args) {
        return new BizException(SystemErrorCodeEnum.SYSTEM_ERROR, message, args);
    }

    public static BizException wrap(BaseErrorCode errorCode, String message, Object... args) {
        return new BizException(errorCode, message, args);
    }

}
