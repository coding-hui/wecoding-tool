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

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import top.wecoding.core.model.request.BaseRequest;
import top.wecoding.core.oss.enums.FileStorageTypeEnum;

/**
 * @author liuyuhui
 * @date 2022/04/07
 * @qq 1515418211
 */
@Data
@Builder
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GetFileBytesRequest extends BaseRequest {

    private String bucket;

    private String path;

    private FileStorageTypeEnum storageType;

    public GetFileBytesRequest(String bucket, String path) {
        this(bucket, path, null);
    }

    public GetFileBytesRequest(String bucket, String path, FileStorageTypeEnum storageType) {
        this.bucket = bucket;
        this.path = path;
        this.storageType = storageType;
    }

}
