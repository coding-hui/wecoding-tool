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
package top.wecoding.core.factory;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import top.wecoding.core.annotion.ErrorCodePrefix;

/**
 * @author liuyuhui
 * @qq 1515418211
 */
public class ErrorCodeFactory {

    private static final String DEFAULT_ERROR_CODE = "";

    public static String buildCode(Class<?> clazz, String code) {

        if (ObjectUtil.isNull(clazz)) {
            return DEFAULT_ERROR_CODE;
        }

        ErrorCodePrefix codePrefix = clazz.getAnnotation(ErrorCodePrefix.class);
        if (codePrefix == null) {
            return DEFAULT_ERROR_CODE;
        }

        StrBuilder resultCode = new StrBuilder();

        if (!StrUtil.startWith(code, codePrefix.app())) {
            resultCode.append(codePrefix.app());
        }

        if (!StrUtil.startWith(code, codePrefix.source())) {
            resultCode.append(codePrefix.source());
        }

        return resultCode.append(code).toString();
    }

}
