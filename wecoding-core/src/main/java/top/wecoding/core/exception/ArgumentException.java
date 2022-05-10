package top.wecoding.core.exception;

import top.wecoding.core.exception.base.BaseErrorCode;
import top.wecoding.core.exception.base.BaseUncheckedException;
import top.wecoding.core.exception.code.ClientErrorCodeEnum;

/**
 * 参数异常
 *
 * @author liuyuhui
 * @date 2022/04/04
 * @qq 1515418211
 */
public class ArgumentException extends BaseUncheckedException {

    public ArgumentException(String errorMessage) {
        super(ClientErrorCodeEnum.REQUEST_PARAMETER_ERROR, errorMessage);
    }

    public ArgumentException(BaseErrorCode errorCode) {
        super(errorCode);
    }

}
