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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author liuyuhui
 * @date 2021/07/28
 * @qq 1515418211
 */
@SuppressWarnings(value = "rawtypes")
public class MultiResponse<T> extends Response {

    private static final long serialVersionUID = 1L;

    protected Collection<T> result;

    public static MultiResponse buildSuccess() {
        MultiResponse response = new MultiResponse();
        response.setSuccess(true);
        response.setErrorCode(DEFAULT_SUCCESS_CODE);
        response.setErrorMessage(DEFAULT_SUCCESS_MESSAGE);
        return response;
    }

    public static MultiResponse buildFailure(String errCode, String errMessage) {
        MultiResponse response = new MultiResponse();
        response.setSuccess(false);
        response.setErrorCode(errCode);
        response.setErrorMessage(errMessage);
        return response;
    }

    public static <T> MultiResponse<T> of(Collection<T> data) {
        MultiResponse<T> response = new MultiResponse<>();
        response.setSuccess(true);
        response.setResult(data);
        response.setErrorCode(DEFAULT_SUCCESS_CODE);
        response.setErrorMessage(DEFAULT_SUCCESS_MESSAGE);
        return response;
    }

    public boolean isEmpty() {
        return result == null || result.size() == 0;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public List<T> getResult() {
        return null == result ? Collections.emptyList() : new ArrayList<>(result);
    }

    public void setResult(Collection<T> result) {
        this.result = result;
    }

}
