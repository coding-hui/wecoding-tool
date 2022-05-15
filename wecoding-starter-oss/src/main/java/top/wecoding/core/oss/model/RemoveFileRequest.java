package top.wecoding.core.oss.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import top.wecoding.core.model.request.BaseRequest;
import top.wecoding.core.oss.enums.FileStorageTypeEnum;

/**
 * @author liuyuhui
 * @date 2022/04/04
 * @qq 1515418211
 */
@Data
@Builder
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class RemoveFileRequest extends BaseRequest {

    private String bucket;

    private String path;

    private FileStorageTypeEnum storageType;

    public RemoveFileRequest(String bucket, String path) {
        this(bucket, path, null);
    }

    public RemoveFileRequest(String bucket, String path, FileStorageTypeEnum storageType) {
        this.bucket = bucket;
        this.path = path;
        this.storageType = storageType;
    }

}
