package top.wecoding.core.factory;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.PageUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.wecoding.core.model.request.PageRequest;
import top.wecoding.core.model.response.PageResult;

import java.util.Collection;
import java.util.Collections;

/**
 * 分页构建工厂
 *
 * @author liuyuhui
 * @qq 1515418211
 */
public class PageFactory {

    public static <T> Page<T> build(PageRequest query) {
        int pageSize = query.getPageSize();
        int current = Math.max(query.getCurrent(), 1);

        return new Page<>(current, pageSize);
    }

    public static PageResult buildPageResult(int pageSize, int current) {
        return buildPageResult(Collections.emptyList(), pageSize, current);
    }

    public static <T> PageResult buildPageResult(Collection<T> records, int pageSize, int current) {
        return buildPageResult(records, 0, pageSize, current);
    }

    public static <T> PageResult buildPageResult(Collection<T> records, int total, int pageSize, int current) {
        return buildPageResult(records, PageUtil.totalPage(Convert.toInt(total), Convert.toInt(pageSize)), total, pageSize, current);
    }

    public static <T> PageResult buildPageResult(Collection<T> records, int totalPage, int total, int pageSize, int current) {
        return PageResult.builder()
                .records(records)
                .totalPage(totalPage)
                .total(total)
                .pageSize(pageSize)
                .current(current)
                .build();
    }

}
