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

/**
 * @author liuyuhui
 * @qq 1515418211
 */
public interface BaseErrorCode {

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

    /**
     * 构建异常提示消息，自定义返回消息
     *
     * @param errorMessage 消息
     * @param param        参数
     * @return ErrorCode
     */
    BaseErrorCode build(String errorMessage, Object... param);

}
