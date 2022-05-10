package top.wecoding.core.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

/**
 * WeCoding 分页参数
 *
 * @author liuyuhui
 * @date 2022/02/19
 * @qq 1515418211
 */
@Data
@Builder
public class PageResult implements Pageable {

    private static final long serialVersionUID = 2470185950461006165L;

    /**
     * 第几页
     */
    private Integer current;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 总记录数
     */
    private Integer total;

    /**
     * 结果集
     */
    private Collection<?> records;

    /**
     * 分页彩虹
     */
    private int[] rainbow;

}
