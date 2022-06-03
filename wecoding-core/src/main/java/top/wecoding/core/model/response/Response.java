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
package top.wecoding.core.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import top.wecoding.core.exception.base.BaseErrorCode;
import top.wecoding.core.model.DTO;

/**
 * @author liuyuhui
 */
@ToString
@Getter
@Setter
@Accessors(chain = true)
public class Response extends DTO {

    public static final String DEFAULT_ERROR_CODE = "B0000";
    public static final String DEFAULT_SUCCESS_CODE = "00000";
    public static final String DEFAULT_SUCCESS_MESSAGE = "请求成功";
    public static final String DEFAULT_ERROR_MESSAGE = "网络异常";
    private static final long serialVersionUID = 1L;
    private boolean success;

    private String errorCode;

    private String errorMessage;

    public static Response buildSuccess() {
        Response response = new Response();
        response.setErrorCode(DEFAULT_SUCCESS_CODE);
        response.setErrorMessage(DEFAULT_SUCCESS_MESSAGE);
        response.setSuccess(true);
        return response;
    }

    public static Response buildFailure() {
        return Response.buildFailure(DEFAULT_ERROR_CODE, DEFAULT_ERROR_MESSAGE);
    }

    public static Response buildFailure(String errMessage) {
        return Response.buildFailure(DEFAULT_ERROR_CODE, errMessage);
    }

    public static Response buildFailure(BaseErrorCode errorCode) {
        return Response.buildFailure(errorCode.getErrorCode(), errorCode.getErrorMessage());
    }

    public static Response buildFailure(BaseErrorCode errorCode, String errMessage) {
        return Response.buildFailure(errorCode.getErrorCode(), errMessage);
    }

    public static Response buildFailure(String errCode, String errMessage) {
        Response response = new Response();
        response.setSuccess(false);
        response.setErrorCode(errCode);
        response.setErrorMessage(errMessage);
        return response;
    }

}
