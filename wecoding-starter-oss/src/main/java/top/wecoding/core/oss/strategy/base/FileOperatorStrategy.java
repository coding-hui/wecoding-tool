package top.wecoding.core.oss.strategy.base;

import cn.hutool.core.io.IoUtil;
import top.wecoding.core.oss.model.*;

import java.io.File;
import java.io.InputStream;

/**
 * 文件操作策略
 *
 * @author liuyuhui
 * @date 2022/04/04
 * @qq 1515418211
 */
public interface FileOperatorStrategy {

    /**
     * 文件存储
     *
     * @param bucketName 桶名称
     * @param key        文件标识，带文件后缀
     * @param file       文件
     * @return 文件对象
     */
    default FileInfoResult storageFile(String bucketName, String key, File file) {
        return storageFile(new StorageFileRequest(bucketName, key, file));
    }

    /**
     * 文件存储
     *
     * @param bucketName 桶名称
     * @param key        文件标识，带文件后缀
     * @param input      文件输入流
     * @return 文件对象
     */
    default FileInfoResult storageFile(String bucketName, String key, InputStream input) {
        return storageFile(new StorageFileRequest(bucketName, key, input));
    }

    /**
     * 文件存储
     *
     * @param bucketName 桶名称
     * @param key        文件标识，带文件后缀
     * @param bytes      文件字节数组
     * @return 文件对象
     */
    default FileInfoResult storageFile(String bucketName, String key, byte[] bytes) {
        return storageFile(new StorageFileRequest(bucketName, key, IoUtil.toStream(bytes)));
    }

    /**
     * 文件存储
     *
     * @param storageFileRequest 文件存储参数
     * @return 文件对象
     */
    FileInfoResult storageFile(StorageFileRequest storageFileRequest);

    /**
     * 删除文件
     *
     * @param bucket 文件桶
     * @param path   文件相对存储路径
     */
    default void removeFile(String bucket, String path) {
        removeFile(new RemoveFileRequest(bucket, path));
    }

    /**
     * 删除文件
     *
     * @param removeFileRequest 删除参数
     */
    void removeFile(RemoveFileRequest removeFileRequest);

    /**
     * 根据文件存储路径获取访问地址，带鉴权
     *
     * @param fileGetParams 查询参数
     * @return 访问路径
     */
    String getFileAuthUrl(GetFileUrlRequest fileGetParams);

    /**
     * 根据文件存储路径获取访问地址，不带鉴权
     *
     * @param fileGetParams 查询参数
     * @return 访问路径
     */
    String getFileUnAuthUrl(GetFileUrlRequest fileGetParams);

    /**
     * 获取文件字节数组，下载使用
     *
     * @param bucket 文件桶
     * @param path   文件相对存储路径
     * @return 文件字节数组
     */
    default byte[] getFileBytes(String bucket, String path) {
        return getFileBytes(new GetFileBytesRequest(bucket, path));
    }

    /**
     * 获取文件字节数组，下载使用
     *
     * @param getFileBytesRequest 请求参数
     * @return 文件字节数组
     */
    byte[] getFileBytes(GetFileBytesRequest getFileBytesRequest);

}
