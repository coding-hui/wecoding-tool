package top.wecoding.core.oss.props;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import top.wecoding.core.oss.enums.FileStorageTypeEnum;

/**
 * 文件存储服务配置文件
 *
 * @author liuyuhui
 * @date 2022/04/04
 * @qq 1515418211
 */
@Setter
@Getter
@ConfigurationProperties(prefix = FileStorageProperties.PREFIX)
public class FileStorageProperties {

    public static final String PREFIX = "wecoding.oss";

    /**
     * 是否启用
     */
    private Boolean enable = true;

    /**
     * 默认存储方式
     */
    private FileStorageTypeEnum defaultStorageType = FileStorageTypeEnum.LOCAL;

    /**
     * 调用系统删除接口时同步删除存储文件
     */
    private Boolean syncDelete = false;

    /**
     * 文件名称最大长度
     */
    private Integer fileNameMax = 100;

    /**
     * 文件大小上限，默认 50M
     */
    private Long maxSize = 50 * 1024 * 1024L;

    private Local local = new Local();

    @Data
    public static class Local {

        /**
         * 资源映射路径 前缀
         */
        public String localFilePrefix = "/profile";

        /**
         * 文件默认存储桶
         */
        private String bucket = "dev";

        /**
         * 域名或本机访问地址
         */
        private String domain = "http://127.0.0.1";

        /**
         * 文件访问前缀
         */
        private String urlPrefix = "";

        /**
         * 文件存储路径
         */
        private String storagePath = "D:/wecoding/upload";

    }

}
