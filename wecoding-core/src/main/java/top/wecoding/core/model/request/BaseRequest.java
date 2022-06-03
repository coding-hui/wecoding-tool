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
package top.wecoding.core.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.wecoding.core.model.DTO;

import java.util.List;

/**
 * 通用查询基类
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseRequest extends DTO {

    private static final long serialVersionUID = 1L;

    /**
     * 搜索值
     */
    private String searchValue;

    /**
     * 数据权限
     */
    private List<Long> dataScope;

    /**
     * 开始时间
     */
    private String searchBeginTime;

    /**
     * 结束时间
     */
    private String searchEndTime;

    /**
     * 状态
     */
    private Integer searchStatus;

    /**
     * 类型
     */
    private List<String> searchTypes;

}
