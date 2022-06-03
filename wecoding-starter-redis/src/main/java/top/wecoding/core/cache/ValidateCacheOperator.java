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
package top.wecoding.core.cache;

import top.wecoding.core.cache.base.BaseMemoryCacheOperator;

/**
 * @author ffd
 * @create 2022-01-21
 * @Description 验证码缓存（登录图形验证码，短信邮箱验证码通用）
 */
public class ValidateCacheOperator extends BaseMemoryCacheOperator<String> {

    public static final String VALIDATE_CODE_CACHE_PREFIX = "validate_";

    @Override
    public String getKeyPrefix() {
        return VALIDATE_CODE_CACHE_PREFIX;
    }

}
