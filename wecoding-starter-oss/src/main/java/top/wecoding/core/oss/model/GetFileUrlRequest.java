package top.wecoding.core.oss.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import top.wecoding.core.model.request.BaseRequest;
import top.wecoding.core.oss.enums.FileStorageTypeEnum;

import javax.validation.constraints.NotBlank;

/**
 * @author liuyuhui
 * @date 2022/04/04
 * @qq 1515418211
 */
@Data
@Builder
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GetFileUrlRequest extends BaseRequest {

    private String bucket;

    @NotBlank(message = "请传入文件路径")
    private String path;

    private FileStorageTypeEnum storageType;

}
