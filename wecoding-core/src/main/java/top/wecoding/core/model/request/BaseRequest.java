package top.wecoding.core.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.wecoding.core.model.DTO;

import java.util.List;

/**
 * 通用查询基类
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseRequest extends DTO {

    private static final long serialVersionUID = 1L;

    /**
     * 搜索值
     */
    private String searchValue;

    /**
     * 数据权限
     */
    private List<Long> dataScope;

    /**
     * 开始时间
     */
    private String searchBeginTime;

    /**
     * 结束时间
     */
    private String searchEndTime;

    /**
     * 状态
     */
    private Integer searchStatus;

    /**
     * 类型
     */
    private List<String> searchTypes;

}
