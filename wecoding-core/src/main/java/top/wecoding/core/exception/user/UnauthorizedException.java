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
package top.wecoding.core.exception.user;

import top.wecoding.core.exception.base.BaseErrorCode;
import top.wecoding.core.exception.base.BaseUncheckedException;
import top.wecoding.core.exception.code.ClientErrorCodeEnum;

/**
 * 认证失败异常
 *
 * @author liuyuhui
 * @date 2022/04/04
 * @qq 1515418211
 */
public class UnauthorizedException extends BaseUncheckedException {

    public UnauthorizedException(BaseErrorCode errorCode) {
        super(errorCode);
    }

    public UnauthorizedException(String errorMessage) {
        super(ClientErrorCodeEnum.NO_PERMISSION_OPERATE, errorMessage);
    }

    public UnauthorizedException(BaseErrorCode errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

}
