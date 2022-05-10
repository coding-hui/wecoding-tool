package top.wecoding.core.model.node;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 通用下拉框节点
 *
 * @author liuyuhui
 * @date 2022/01/24
 * @qq 1515418211
 */
@Data
@Accessors(chain = true)
public class BaseOptionNode {

    private Serializable id;

    private String code;

    private String label;

    private String value;

}
