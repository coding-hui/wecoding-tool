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
 * 用户性别
 *
 * @author liuyuhui
 * @qq 1515418211
 * @date 2021/07/28
 */
@Getter
public enum GenderEnum {

    /**
     * 男
     */
    MAN("1", "男"),

    /**
     * 女
     */
    WOMAN("2", "女"),

    /**
     * 未知
     */
    NONE("3", "未知");

    private final String code;

    private final String message;

    GenderEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
