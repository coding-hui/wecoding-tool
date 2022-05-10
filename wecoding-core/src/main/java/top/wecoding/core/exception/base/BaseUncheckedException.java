package top.wecoding.core.exception.base;

import top.wecoding.core.exception.code.SystemErrorCodeEnum;

/**
 * base unchecked exceptions
 *
 * @author liuyuhui
 * @qq 1515418211
 */
public class BaseUncheckedException extends RuntimeException implements BaseException {

    private static final long serialVersionUID = 1L;

    private final BaseErrorCode errorCode;

    private final String errorMessage;

    public BaseUncheckedException(String errorMessage) {
        super(errorMessage);
        this.errorCode = SystemErrorCodeEnum.SYSTEM_ERROR;
        this.errorMessage = errorMessage;
    }

    public BaseUncheckedException(BaseErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getErrorMessage();
    }

    public BaseUncheckedException(final BaseErrorCode errorCode, Throwable e) {
        super(errorCode.getErrorMessage(), e);
        this.errorMessage = errorCode.getErrorMessage();
        this.errorCode = errorCode;
    }

    public BaseUncheckedException(final BaseErrorCode errorCode, final String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BaseUncheckedException(final BaseErrorCode errorCode, final String errorMessage, Throwable e) {
        super(errorMessage, e);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BaseUncheckedException(final BaseErrorCode errorCode, final String format, Object... args) {
        super(BaseException.wrapErrorMessage(format, args));
        this.errorCode = errorCode;
        this.errorMessage = BaseException.wrapErrorMessage(format, args);
    }

    @Override
    public String getErrorCode() {
        return errorCode.getErrorCode();
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

}
