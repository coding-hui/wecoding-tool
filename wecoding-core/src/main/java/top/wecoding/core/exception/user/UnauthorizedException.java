package top.wecoding.core.exception.user;

import top.wecoding.core.exception.base.BaseErrorCode;
import top.wecoding.core.exception.base.BaseUncheckedException;
import top.wecoding.core.exception.code.ClientErrorCodeEnum;

/**
 * 认证失败异常
 *
 * @author liuyuhui
 * @date 2022/04/04
 * @qq 1515418211
 */
public class UnauthorizedException extends BaseUncheckedException {

    public UnauthorizedException(BaseErrorCode errorCode) {
        super(errorCode);
    }

    public UnauthorizedException(String errorMessage) {
        super(ClientErrorCodeEnum.LOGIN_ERROR, errorMessage);
    }

    public UnauthorizedException(BaseErrorCode errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

}
