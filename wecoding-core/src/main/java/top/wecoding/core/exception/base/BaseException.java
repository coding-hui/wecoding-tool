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
package top.wecoding.core.exception.base;

import cn.hutool.core.util.StrUtil;

/**
 * 异常接口规范
 *
 * @author liuyuhui
 * @date 2022/04/04
 * @qq 1515418211
 */
public interface BaseException {

    /**
     * 包装自定义异常信息
     *
     * @param format 异常信息
     * @param args   参数
     * @return 自定义的异常信息
     */
    static String wrapErrorMessage(final String format, Object... args) {
        return StrUtil.format(format, args);
    }

    /**
     * 获取异常的状态码
     *
     * @return 状态码
     */
    String getErrorCode();

    /**
     * 获取异常的提示信息
     *
     * @return 错误信息
     */
    String getErrorMessage();

}
