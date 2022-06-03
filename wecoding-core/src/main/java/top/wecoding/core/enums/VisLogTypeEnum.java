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
 * 访问日志类型枚举
 *
 * @author liuyuhui
 * @date 2022/4/30
 * @qq 1515418211
 */
@Getter
public enum VisLogTypeEnum {

    /**
     * 登录日志
     */
    LOGIN("0", "登录成功"),

    /**
     * 登录日志
     */
    LOGIN_ERROR("1", "登录失败"),


    /**
     * 退出日志
     */
    EXIT("2", "登出");

    private final String code;

    private final String message;

    VisLogTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
