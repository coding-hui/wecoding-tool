package top.wecoding.core.oss.enums;

import lombok.Getter;

/**
 * @author liuyuhui
 * @date 2022/04/04
 * @qq 1515418211
 */
@Getter
public enum FileStorageTypeEnum {

    /**
     * 文件存储策略
     */
    LOCAL("本地"),
    FAST_DFS("FastDFS"),
    MIN_IO("MinIO"),
    ALI_OSS("阿里云"),
    QINIU_OSS("七牛云OSS");

    private final String typeName;

    FileStorageTypeEnum(String typeName) {
        this.typeName = typeName;
    }

    public boolean equals(FileStorageTypeEnum type) {
        return type != null && this.name().equals(type.name());
    }

}
