package top.wecoding.core.exception;

import top.wecoding.core.exception.base.BaseErrorCode;
import top.wecoding.core.exception.base.BaseUncheckedException;

/**
 * 远程服务异常
 *
 * @author liuyuhui
 * @date 2022/01/22
 * @qq 1515418211
 */
public class RemoteException extends BaseUncheckedException {

    private static final long serialVersionUID = 1L;

    public RemoteException(String errorMessage) {
        super(errorMessage);
    }

    public RemoteException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }

    public RemoteException(BaseErrorCode baseErrorCode, String errorMessage) {
        super(baseErrorCode, errorMessage);
    }

    public RemoteException(BaseErrorCode baseErrorCode, Throwable e) {
        super(baseErrorCode, e);
    }

}
