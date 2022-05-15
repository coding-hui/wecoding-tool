package top.wecoding.core.oss.strategy.base;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import top.wecoding.core.constant.StrPool;
import top.wecoding.core.exception.ArgumentException;
import top.wecoding.core.exception.Assert;
import top.wecoding.core.exception.BizException;
import top.wecoding.core.exception.code.SystemErrorCodeEnum;
import top.wecoding.core.oss.model.FileInfoResult;
import top.wecoding.core.oss.model.StorageFileRequest;
import top.wecoding.core.oss.props.FileStorageProperties;
import top.wecoding.core.oss.util.MimeTypeUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.StringJoiner;

/**
 * 文件抽象策略处理类，封装公共操作方法
 *
 * @author liuyuhui
 * @date 2022/04/04
 * @qq 1515418211
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractFileOperatorStrategy implements FileOperatorStrategy {

    protected final FileStorageProperties fileStorageProperties;

    @Override
    public FileInfoResult storageFile(StorageFileRequest storageFileRequest) {
        try {
            // 1. 检验文件是否可以上传
            assertAllowed(storageFileRequest);
            // 2. 填充请求参数
            fillRequestMetadata(storageFileRequest);
            // 3. 执行具体上传逻辑
            return doUpload(storageFileRequest);
        } catch (BizException | ArgumentException e) {
            log.error(" >>> 上传文件错误:", e);
            throw e;
        } catch (Exception e) {
            log.error(" >>> 上传文件错误:", e);
            throw BizException.wrap(SystemErrorCodeEnum.FILE_UPLOAD_ERROR, e);
        }
    }

    /**
     * 各具体策略实现
     *
     * @param storageFileRequest 上传文件参数
     * @return 上传的文件信息
     * @throws Exception 错误异常
     */
    protected abstract FileInfoResult doUpload(StorageFileRequest storageFileRequest) throws Exception;

    /**
     * 构建文件信息
     */
    protected void fillRequestMetadata(StorageFileRequest storageFileRequest) throws IOException {
        File file = storageFileRequest.getLocalFile();
        InputStream inputStream = IoUtil.toMarkSupportStream(storageFileRequest.getInputStream());
        String key = storageFileRequest.getKey();
        String filename = FileNameUtil.getName(key);
        String suffix = FileNameUtil.getSuffix(filename);
        String uniqueName = extractUniqueFilename(suffix);
        String path = getPath(uniqueName);
        String contentType = FileUtil.getMimeType(filename);

        long size;
        if (file == null) {
            size = inputStream.available();
        } else {
            size = file.length();
        }
        inputStream.mark(28);
        String fileType = FileTypeUtil.getType(inputStream, filename);
        inputStream.reset();

        storageFileRequest.setOriginalName(filename);
        storageFileRequest.setContentType(contentType);
        storageFileRequest.setSize(size);
        storageFileRequest.setSuffix(suffix);
        storageFileRequest.setFileType(fileType);
        storageFileRequest.setUniqueName(uniqueName);
        storageFileRequest.setPath(path);
        storageFileRequest.setInputStream(inputStream);
    }

    protected FileInfoResult createResultInfo(StorageFileRequest storageFileRequest) {
        FileInfoResult result = new FileInfoResult();
        BeanUtil.copyProperties(storageFileRequest, result);
        return result;
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension        上传文件类型
     * @param allowedExtension 允许上传文件类型
     * @return true/false
     */
    protected boolean isAllowedExtension(String extension, String[] allowedExtension) {
        return ArrayUtil.containsAny(allowedExtension, extension);
    }

    /**
     * /业务类型/年/月/日/唯一文件名
     */
    protected String getPath(String uniqueFileName) {
        return new StringJoiner(StrPool.LEFT_DIVIDE)
                .add(getDatePath())
                .add(uniqueFileName)
                .toString();
    }

    /**
     * 文件唯一名称
     */
    protected String extractUniqueFilename(String suffix) {
        return new StringJoiner(StrPool.PERIOD)
                .add(IdUtil.fastSimpleUUID())
                .add(suffix)
                .toString();
    }

    /**
     * 年/月/日
     */
    protected String getDatePath() {
        return DateUtil.format(new Date(), "yyyy/MM/dd");
    }

    /**
     * 文件上传校验
     */
    private void assertAllowed(StorageFileRequest storageFileRequest) throws IOException {
        // 文件非空检验
        Assert.notNull(storageFileRequest, "不能上传空文件");

        String key = storageFileRequest.getKey();
        File file = storageFileRequest.getLocalFile();
        InputStream inputStream = IoUtil.toAvailableStream(storageFileRequest.getInputStream());

        Assert.notBlank(key, "文件标识不能为空");
        Assert.isTrue(!ArrayUtil.isAllNull(file, inputStream), "不能上传空文件");

        // 名称长度校验
        String filename = FileNameUtil.getName(key);
        int fileNameLen = filename.length();
        if (fileNameLen > fileStorageProperties.getFileNameMax()) {
            throw new BizException(SystemErrorCodeEnum.FILE_UPLOAD_ERROR
                    .build("文件名称长度不可超过{}", fileStorageProperties.getFileNameMax()));
        }

        // 文件大小校验
        long size = ObjectUtil.isNull(file) ? inputStream.available() : file.length();
        if (size > fileStorageProperties.getMaxSize()) {
            throw BizException.wrap(SystemErrorCodeEnum.FILE_UPLOAD_ERROR
                    .build("文件大小不可超过{}M", (fileStorageProperties.getMaxSize() / 1024 / 1024)));
        }

        // 文件类型检验
        String extension = FileNameUtil.getSuffix(key);
        if (!isAllowedExtension(extension, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION)) {
            throw BizException.wrap(SystemErrorCodeEnum.FILE_TYPE_NOT_ALLOWED
                    .build("不可上传.{}类型文件", extension));
        }
    }

}
