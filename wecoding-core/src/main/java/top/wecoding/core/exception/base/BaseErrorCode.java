package top.wecoding.core.exception.base;

/**
 * @author liuyuhui
 * @qq 1515418211
 */
public interface BaseErrorCode {

    /**
     * 获取异常的状态码
     *
     * @return 状态码
     */
    String getErrorCode();

    /**
     * 获取异常的提示信息
     *
     * @return 错误信息
     */
    String getErrorMessage();

    /**
     * 构建异常提示消息，自定义返回消息
     *
     * @param errorMessage 消息
     * @param param        参数
     * @return ErrorCode
     */
    BaseErrorCode build(String errorMessage, Object... param);

}
