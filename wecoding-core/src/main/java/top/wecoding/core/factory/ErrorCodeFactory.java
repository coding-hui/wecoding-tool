package top.wecoding.core.factory;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import top.wecoding.core.annotion.ErrorCodePrefix;

/**
 * @author liuyuhui
 * @qq 1515418211
 */
public class ErrorCodeFactory {

    private static final String DEFAULT_ERROR_CODE = "";

    public static String buildCode(Class<?> clazz, String code) {

        if (ObjectUtil.isNull(clazz)) {
            return DEFAULT_ERROR_CODE;
        }

        ErrorCodePrefix codePrefix = clazz.getAnnotation(ErrorCodePrefix.class);
        if (codePrefix == null) {
            return DEFAULT_ERROR_CODE;
        }

        StrBuilder resultCode = new StrBuilder();

        if (!StrUtil.startWith(code, codePrefix.app())) {
            resultCode.append(codePrefix.app());
        }

        if (!StrUtil.startWith(code, codePrefix.source())) {
            resultCode.append(codePrefix.source());
        }

        return resultCode.append(code).toString();
    }

}
