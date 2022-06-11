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
package top.wecoding.core.exception.code;

import cn.hutool.core.util.StrUtil;
import top.wecoding.core.annotion.ErrorCodePrefix;
import top.wecoding.core.constant.ErrorCodeConstant;
import top.wecoding.core.exception.base.BaseErrorCode;
import top.wecoding.core.factory.ErrorCodeFactory;

/**
 * @author liuyuhui
 * @qq 1515418211
 */
@ErrorCodePrefix(source = ErrorCodeConstant.SYSTEM_ERROR)
public enum SystemErrorCodeEnum implements BaseErrorCode {

    /**
     * 系统错误 一级宏观错误码
     */
    SYSTEM_ERROR("0001", "系统出现异常，请联系管理员"),

    /**
     * 运行超时 二级宏观错误码
     */
    SYSTEM_EXECUTION_TIMEOUT("0100", "系统运行超时，请稍后再试"),
    REQUEST_NULL_POINT("0110", "空指针异常"),

    /**
     * 系统资源异常 二级宏观错误码
     */
    SYSTEM_RESOURCES_ERROR("0200", "系统资源异常"),
    SYSTEM_RESOURCES_EMPTY("0210", "系统资源耗尽"),
    SYSTEM_THREAT_POOL_EMPTY("0220", "系统线程池耗尽"),
    ACCESS_RESOURCES_ERROR("0220", "系统资源访问异常"),

    /**
     * SQL异常 二级宏观错误码
     */
    SQL_ERROR("0300", "运行SQL异常");

    private final String errorCode;

    private String errorMessage;

    SystemErrorCodeEnum(String errorCode, String errorMessage) {
        this.errorCode = ErrorCodeFactory.buildCode(SystemErrorCodeEnum.class, errorCode);
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorCode() {
        return ErrorCodeFactory.buildCode(SystemErrorCodeEnum.class, errorCode);
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public BaseErrorCode build(String errorMessage, Object... param) {
        this.errorMessage = StrUtil.format(errorMessage, param);
        return this;
    }

}
