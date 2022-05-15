package top.wecoding.core.oss.model;

import lombok.Data;
import lombok.experimental.Accessors;
import top.wecoding.core.oss.enums.FileStorageTypeEnum;

import java.io.Serializable;

/**
 * @author liuyuhui
 * @date 2022/04/06
 * @qq 1515418211
 */
@Data
@Accessors(chain = true)
public class FileInfoResult implements Serializable {

    private static final long serialVersionUID = -3451881972260803488L;

    /**
     * 唯一Id
     */
    private Long id;

    /**
     * 文件存储桶
     */
    private String bucket;

    /**
     * 文件标识
     */
    private String key;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件相对地址
     */
    private String path;

    /**
     * 文件访问地址
     */
    private String url;

    /**
     * 唯一文件名
     */
    private String uniqueName;

    /**
     * 原始文件名
     */
    private String originalName;

    /**
     * 文件后缀
     */
    private String suffix;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件md5
     */
    private String md5;

    /**
     * 文件内容类型
     */
    private String contentType;

    /**
     * 存储类型
     */
    private FileStorageTypeEnum storageType;

}
