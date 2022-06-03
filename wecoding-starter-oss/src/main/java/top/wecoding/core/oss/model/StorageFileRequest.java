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
package top.wecoding.core.oss.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import top.wecoding.core.model.request.BaseRequest;
import top.wecoding.core.oss.enums.FileStorageTypeEnum;

import java.io.File;
import java.io.InputStream;

/**
 * @author liuyuhui
 * @date 2022/04/06
 * @qq 1515418211
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class StorageFileRequest extends BaseRequest {

    /**
     * 文件存储桶
     */
    private String bucket;

    /**
     * 存储类型
     */
    private FileStorageTypeEnum storageType;

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
     * 文件输入流
     */
    private InputStream inputStream;

    /**
     * 文件
     */
    private File localFile;

    public StorageFileRequest() {
    }

    public StorageFileRequest(String bucket, String key, File localFile) {
        this.bucket = bucket;
        this.key = key;
        this.localFile = localFile;
    }

    public StorageFileRequest(String bucket, String key, InputStream inputStream) {
        this.bucket = bucket;
        this.key = key;
        this.inputStream = inputStream;
    }

}
