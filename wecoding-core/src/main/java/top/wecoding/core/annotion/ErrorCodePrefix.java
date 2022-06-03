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
package top.wecoding.core.annotion;

import java.lang.annotation.*;

/**
 * 标识在异常枚举类上，用来标识类级别异常枚举编码
 * <p>
 * 错误码为字符串类型，分成三个部分：
 * 错误应用平台(可选) - 错误产生来源(一位) + 四位数字编号
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ErrorCodePrefix {

    /**
     * 错误应用平台
     */
    String app() default "";

    /**
     * 错误产生来源
     */
    String source();

}
