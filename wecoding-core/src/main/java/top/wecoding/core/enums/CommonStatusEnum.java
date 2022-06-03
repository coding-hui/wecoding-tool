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
package top.wecoding.core.enums;

import lombok.Getter;
import top.wecoding.core.exception.ArgumentException;
import top.wecoding.core.exception.Assert;
import top.wecoding.core.exception.BizException;
import top.wecoding.core.exception.code.ClientErrorCodeEnum;

/**
 * 公共状态
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Getter
public enum CommonStatusEnum {

    /**
     * 正常
     */
    ENABLE("0", "正常"),

    /**
     * 停用
     */
    DISABLE("1", "停用"),

    /**
     * 删除
     */
    DELETED("2", "删除");

    private final String code;

    private final String message;

    CommonStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 检查请求参数的状态是否正确
     */
    public static void validateStatus(String code) {
        Assert.notNull(code, () -> new ArgumentException(ClientErrorCodeEnum.REQUEST_PARAMETER_ERROR));

        if (ENABLE.getCode().equals(code) || DISABLE.getCode().equals(code)) {
            return;
        }

        throw new BizException(ClientErrorCodeEnum.NOT_WRITE_STATUS);
    }

}
