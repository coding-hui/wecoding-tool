package top.wecoding.core.oss.strategy;

import cn.hutool.core.convert.Convert;
import lombok.extern.slf4j.Slf4j;
import top.wecoding.core.exception.Assert;
import top.wecoding.core.oss.enums.FileStorageTypeEnum;
import top.wecoding.core.oss.model.*;
import top.wecoding.core.oss.props.FileStorageProperties;
import top.wecoding.core.oss.strategy.base.FileOperatorStrategy;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文件操作策略上下文
 *
 * @author liuyuhui
 * @date 2022/04/04
 * @qq 1515418211
 */
@Slf4j
public class FileStorageContext implements FileOperatorStrategy {

    private final Map<String, FileOperatorStrategy> fileOperatorStrategyMap = new ConcurrentHashMap<>();
    private final FileStorageProperties fileStorageProperties;

    public FileStorageContext(Map<String, FileOperatorStrategy> fileOperatorStrategyMap,
                              FileStorageProperties fileStorageProperties) {
        this.fileOperatorStrategyMap.putAll(fileOperatorStrategyMap);
        this.fileStorageProperties = fileStorageProperties;
    }

    /**
     * 获取默认文件存储类型策略
     */
    public FileOperatorStrategy getDefaultStrategy() {
        Assert.state(fileStorageProperties.getEnable(), "文件存储服务未启用");
        return fileOperatorStrategyMap.get(fileStorageProperties.getDefaultStorageType().name());
    }

    /**
     * 获取文件存储类型策略
     *
     * @param storageType 存储类型
     * @return FileOperatorStrategy 具体实现
     */
    public FileOperatorStrategy getFileStrategy(FileStorageTypeEnum storageType) {
        Assert.state(fileStorageProperties.getEnable(), "文件存储服务未启用");

        storageType = Convert.toEnum(FileStorageTypeEnum.class, storageType, fileStorageProperties.getDefaultStorageType());
        FileOperatorStrategy fileStrategy = fileOperatorStrategyMap.get(storageType.name());

        Assert.notNull(fileStrategy, "不支持该文件存储类型");

        return fileStrategy;
    }

    /**
     * 是否同步删除存储文件
     */
    public boolean isSyncDeleteFile() {
        return fileStorageProperties.getSyncDelete();
    }

    @Override
    public FileInfoResult storageFile(String bucketName, String key, File file) {
        return getDefaultStrategy().storageFile(bucketName, key, file);
    }

    @Override
    public FileInfoResult storageFile(String bucketName, String key, InputStream input) {
        return getDefaultStrategy().storageFile(bucketName, key, input);
    }

    @Override
    public FileInfoResult storageFile(String bucketName, String key, byte[] bytes) {
        return getDefaultStrategy().storageFile(bucketName, key, bytes);
    }

    @Override
    public FileInfoResult storageFile(StorageFileRequest storageFileRequest) {
        // 获取文件上传策略
        FileOperatorStrategy fileStrategy = getFileStrategy(storageFileRequest.getStorageType());
        // 上传文件
        return fileStrategy.storageFile(storageFileRequest);
    }

    @Override
    public void removeFile(String bucket, String path) {
        getDefaultStrategy().removeFile(bucket, path);
    }

    @Override
    public void removeFile(RemoveFileRequest removeFileRequest) {
        FileOperatorStrategy fileStrategy = getFileStrategy(removeFileRequest.getStorageType());
        fileStrategy.removeFile(removeFileRequest);
    }

    @Override
    public String getFileAuthUrl(GetFileUrlRequest fileGetParams) {
        FileOperatorStrategy fileStrategy = getFileStrategy(fileGetParams.getStorageType());
        return fileStrategy.getFileAuthUrl(fileGetParams);
    }

    @Override
    public String getFileUnAuthUrl(GetFileUrlRequest fileGetParams) {
        FileOperatorStrategy fileStrategy = getFileStrategy(fileGetParams.getStorageType());
        return fileStrategy.getFileUnAuthUrl(fileGetParams);
    }

    @Override
    public byte[] getFileBytes(String bucket, String path) {
        return getDefaultStrategy().getFileBytes(bucket, path);
    }

    @Override
    public byte[] getFileBytes(GetFileBytesRequest getFileBytesRequest) {
        FileOperatorStrategy fileStrategy = getFileStrategy(getFileBytesRequest.getStorageType());
        return fileStrategy.getFileBytes(getFileBytesRequest);
    }

}
