package top.wecoding.core.exception.user;

import top.wecoding.core.exception.base.BaseUncheckedException;
import top.wecoding.core.exception.code.ClientErrorCodeEnum;

/**
 * 内部认证异常
 *
 * @author liuyuhui
 * @qq 1515418211
 */
public class InnerAuthException extends BaseUncheckedException {

    public InnerAuthException(String message) {
        super(ClientErrorCodeEnum.INNER_AUTH_ERROR, message);
    }

}
