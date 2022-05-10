package top.wecoding.core.model.response;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.wecoding.core.factory.PageFactory;

import java.util.Collection;

/**
 * @author liuyuhui
 * @qq 151541821i
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings(value = "rawtypes")
public class PageResponse<T> extends Response {

    private static final long serialVersionUID = 1L;

    private PageResult result;

    public static PageResponse buildSuccess() {
        PageResponse response = new PageResponse();
        response.setSuccess(true);
        response.setErrorCode(DEFAULT_SUCCESS_CODE);
        response.setErrorMessage(DEFAULT_SUCCESS_MESSAGE);
        return response;
    }

    public static PageResponse buildFailure(String errMessage) {
        return buildFailure(DEFAULT_ERROR_CODE, errMessage);
    }

    public static PageResponse buildFailure(String errCode, String errMessage) {
        PageResponse response = new PageResponse();
        response.setSuccess(false);
        response.setErrorCode(errCode);
        response.setErrorMessage(errMessage);
        return response;
    }

    public static <T> PageResponse<T> of(Page<T> page) {
        int current = Convert.toInt(page.getCurrent());
        int pageSize = Convert.toInt(page.getSize());
        int total = Convert.toInt(page.getTotal());
        return PageResponse.of(PageFactory.buildPageResult(page.getRecords(), total, pageSize, current));
    }

    public static <T> PageResponse<T> of(int pageSize, int current) {
        return PageResponse.of(PageFactory.buildPageResult(pageSize, current));
    }

    public static <T> PageResponse<T> of(Collection<T> rows, int totalPage, int pageSize, int current) {
        return PageResponse.of(PageFactory.buildPageResult(rows, totalPage, pageSize, current));
    }

    public static <T> PageResponse<T> of(PageResult pageResult) {
        PageResponse<T> response = new PageResponse<>();
        response.setSuccess(true);
        response.setResult(pageResult);
        response.setErrorCode(DEFAULT_SUCCESS_CODE);
        response.setErrorMessage(DEFAULT_SUCCESS_MESSAGE);
        return response;
    }

}