package top.wecoding.core.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Data
@Accessors(chain = true)
@TableName(value = "sys_op_log")
public class SysOpLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 跟踪 ID
     */
    private String traceId;

    /**
     * 日志类型
     */
    private String type;

    /**
     * 日志标题
     */
    private String title;

    /**
     * 操作 IP 地址
     */
    private String ip;

    /**
     * 地区
     */
    private String location;

    /**
     * 用户浏览器
     */
    private String userAgent;

    /**
     * 请求 URI
     */
    private String requestUri;

    /**
     * 操作方式
     */
    private String requestMethod;

    /**
     * 操作提交的数据
     */
    private String params;

    /**
     * 执行时间
     */
    private Long time;

    /**
     * 异常信息
     */
    private String exception;

    /**
     * 服务ID
     */
    private String serviceId;

    /**
     * Class Name
     */
    private String method;

    /**
     * 业务类型
     */
    private Integer businessType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

}
