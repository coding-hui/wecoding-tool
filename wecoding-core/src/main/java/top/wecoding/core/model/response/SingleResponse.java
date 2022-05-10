package top.wecoding.core.model.response;

import top.wecoding.core.exception.base.BaseErrorCode;

/**
 * @author liuyuhui
 * @qq 1515418211
 */
@SuppressWarnings(value = "rawtypes")
public class SingleResponse<T> extends Response {

    private static final long serialVersionUID = 1L;

    private T result;

    public static SingleResponse buildSuccess() {
        SingleResponse response = new SingleResponse();
        response.setSuccess(true);
        response.setErrorCode(DEFAULT_SUCCESS_CODE);
        response.setErrorMessage(DEFAULT_SUCCESS_MESSAGE);
        return response;
    }

    public static SingleResponse buildFailure(BaseErrorCode errorCode) {
        return buildFailure(errorCode.getErrorCode(), errorCode.getErrorMessage());
    }

    public static SingleResponse buildFailure(String errMessage) {
        return buildFailure(DEFAULT_ERROR_CODE, errMessage);
    }

    public static SingleResponse buildFailure(String errCode, String errMessage) {
        SingleResponse response = new SingleResponse();
        response.setSuccess(false);
        response.setErrorCode(errCode);
        response.setErrorMessage(errMessage);
        return response;
    }

    public static <T> SingleResponse<T> of(T data) {
        SingleResponse<T> response = new SingleResponse<>();
        response.setSuccess(true);
        response.setResult(data);
        response.setErrorCode(DEFAULT_SUCCESS_CODE);
        response.setErrorMessage(DEFAULT_SUCCESS_MESSAGE);
        return response;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
