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

/**
 * @author liuyuhui
 * @qq 1515418211
 */
@Getter
public enum UserStatusEnum {

    /**
     * 用户状态
     */
    OK("0", "正常"),

    DISABLE("1", "停用"),

    DELETED("2", "删除");

    private final String code;

    private final String info;

    UserStatusEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

}
