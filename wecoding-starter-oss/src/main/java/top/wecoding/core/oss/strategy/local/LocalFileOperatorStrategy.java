/*
 * Copyright (c) 2022. WeCoding (wecoding@yeah.net).
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/lgpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.wecoding.core.oss.strategy.local;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import top.wecoding.core.constant.StrPool;
import top.wecoding.core.exception.Assert;
import top.wecoding.core.exception.BizException;
import top.wecoding.core.exception.code.ClientErrorCodeEnum;
import top.wecoding.core.oss.enums.FileStorageTypeEnum;
import top.wecoding.core.oss.model.*;
import top.wecoding.core.oss.props.FileStorageProperties;
import top.wecoding.core.oss.strategy.base.AbstractFileOperatorStrategy;

import java.io.InputStream;
import java.nio.file.Paths;

/**
 * 本地文件存储策略实现
 *
 * @author liuyuhui
 * @date 2022/04/04
 * @qq 1515418211
 */
public class LocalFileOperatorStrategy extends AbstractFileOperatorStrategy {

    private final FileStorageProperties.Local local;

    public LocalFileOperatorStrategy(FileStorageProperties fileStorageProperties) {
        super(fileStorageProperties);
        this.local = fileStorageProperties.getLocal();
    }

    @Override
    protected FileInfoResult doUpload(StorageFileRequest storageFileRequest) {
        String bucket = StrUtil.isEmpty(storageFileRequest.getBucket()) ? local.getBucket() : storageFileRequest.getBucket();
        String path = storageFileRequest.getPath();
        InputStream inputStream = storageFileRequest.getInputStream();

        // 获取存储本地的绝对路径
        String absoluteFilePath = getAbsoluteFile(local.getStoragePath(), bucket, path);
        // 保存文件
        FileUtil.writeFromStream(inputStream, absoluteFilePath);
        // 访问路径
        String urlPrefix = local.getUrlPrefix();
        String url = local.getDomain() + urlPrefix + StrPool.LEFT_DIVIDE + bucket + StrPool.LEFT_DIVIDE + path;

        return createResultInfo(storageFileRequest)
                .setBucket(bucket)
                .setUrl(url)
                .setStorageType(FileStorageTypeEnum.LOCAL);
    }

    @Override
    public void removeFile(RemoveFileRequest removeFileRequest) {
        String bucket = removeFileRequest.getBucket();
        String path = removeFileRequest.getPath();

        Assert.notBlank(bucket, "文件存储桶不能为空");
        Assert.notBlank(path, "文件存储位置不能为空");

        FileUtil.del(getAbsoluteFile(local.getStoragePath(), bucket, path));
    }

    @Override
    public String getFileAuthUrl(GetFileUrlRequest fileGetParams) {
        return getFileUnAuthUrl(fileGetParams);
    }

    @Override
    public String getFileUnAuthUrl(GetFileUrlRequest fileGetParams) {
        String bucket = fileGetParams.getBucket();
        String path = fileGetParams.getPath();

        Assert.notBlank(bucket, "文件存储桶不能为空");
        Assert.notBlank(path, "文件存储位置不能为空");

        String urlPrefix = local.getUrlPrefix();
        return local.getDomain() +
                urlPrefix +
                StrPool.LEFT_DIVIDE +
                bucket +
                StrPool.LEFT_DIVIDE +
                path;
    }

    @Override
    public byte[] getFileBytes(GetFileBytesRequest getFileBytesRequest) {
        String bucket = getFileBytesRequest.getBucket();
        String path = getFileBytesRequest.getPath();

        Assert.notBlank(bucket, "文件存储桶不能为空");
        Assert.notBlank(path, "文件存储位置不能为空");

        try {
            return FileUtil.readBytes(getAbsoluteFile(local.getStoragePath(), bucket, path));
        } catch (Exception e) {
            throw new BizException(ClientErrorCodeEnum.FILE_DOWNLOAD_ERROR);
        }
    }

    /**
     * 获取文件存储绝对路径
     *
     * @param uploadDir 上传路径
     * @param bucket    桶
     * @param fileName  文件名称
     * @return 上传的文件
     */
    private String getAbsoluteFile(String uploadDir, String bucket, String fileName) {
        return Paths.get(uploadDir, bucket, fileName).toString();
    }

}
