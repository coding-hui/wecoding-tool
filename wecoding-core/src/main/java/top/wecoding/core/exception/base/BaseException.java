package top.wecoding.core.exception.base;

import cn.hutool.core.util.StrUtil;

/**
 * 异常接口规范
 *
 * @author liuyuhui
 * @date 2022/04/04
 * @qq 1515418211
 */
public interface BaseException {

    /**
     * 包装自定义异常信息
     *
     * @param format 异常信息
     * @param args   参数
     * @return 自定义的异常信息
     */
    static String wrapErrorMessage(final String format, Object... args) {
        return StrUtil.format(format, args);
    }

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

}
