package top.wecoding.core.exception;

import top.wecoding.core.exception.base.BaseUncheckedException;
import top.wecoding.core.exception.code.ClientErrorCodeEnum;

/**
 * 演示无法操作异常
 *
 * @author liuyuhui
 * @date 2021/08/09
 * @qq 1515418211
 */
public class DemoException extends BaseUncheckedException {

    public DemoException() {
        super(ClientErrorCodeEnum.DEMO_EXP_ERROR);
    }

}
