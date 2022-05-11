package top.wecoding.core.web.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import top.wecoding.core.model.response.Response;

import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * @author liuyuhui
 */
@Slf4j
public class BaseController {

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtil.parse(text, DatePattern.NORM_DATETIME_PATTERN));
            }
        });
    }

    public Response toResponse(boolean success) {
        return success ? Response.buildSuccess() : Response.buildFailure();
    }

}
