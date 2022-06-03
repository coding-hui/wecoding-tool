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
package top.wecoding.core.log.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author liuyuhui
 * @date 2021/09/07
 * @qq 1515418211
 */
@Getter
@RequiredArgsConstructor
public enum LogTypeEnum {

    /**
     * 正常
     */
    NORMAL("1", "正常日志"),

    /**
     * 错误
     */
    ERROR("0", "异常日志");

    /**
     * 类型
     */
    private final String type;

    /**
     * 描述
     */
    private final String description;

}
