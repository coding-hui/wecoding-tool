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

/**
 * 分页查询
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageRequest extends BaseRequest {

    public static final String ASC = "ASC";

    public static final String DESC = "DESC";

    private static final int DEFAULT_PAGE_SIZE = 10;

    private Integer pageSize;

    private Integer current;

    private String orderBy;

    private String orderDirection;

    private String groupBy;

    private Boolean needTotalCount;

    public PageRequest() {
        this.pageSize = DEFAULT_PAGE_SIZE;
        this.current = 1;
        this.orderDirection = DESC;
        this.needTotalCount = Boolean.TRUE;
    }

    public int getCurrent() {
        if (current < 1) {
            current = 1;
        }
        return current;
    }

    public PageRequest setCurrent(int current) {
        this.current = current;
        return this;
    }

    public int getPageSize() {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public PageRequest setPageSize(int pageSize) {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.pageSize = pageSize;
        return this;
    }

    public int getOffset() {
        return (getCurrent() - 1) * getPageSize();
    }

    public String getOrderBy() {
        return orderBy;
    }

    public PageRequest setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public PageRequest setOrderDirection(String orderDirection) {
        if (ASC.equalsIgnoreCase(orderDirection) || DESC.equalsIgnoreCase(orderDirection)) {
            this.orderDirection = orderDirection;
        }
        return this;
    }

}
