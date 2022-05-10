package top.wecoding.core.exception.user;

import top.wecoding.core.exception.base.BaseErrorCode;
import top.wecoding.core.exception.base.BaseUncheckedException;

/**
 * 禁止访问异常
 *
 * @author liuyuhui
 * @date 2022/04/04
 * @qq 1515418211
 */
public class ForbiddenException extends BaseUncheckedException {

    public ForbiddenException(BaseErrorCode errorCode) {
        super(errorCode);
    }

}
