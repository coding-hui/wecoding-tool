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
package top.wecoding.core.security.registry;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * API 放行配置
 *
 * @author liuyuhui
 * @date 2022/6/6
 * @qq 1515418211
 */
@Data
public class SecurityRegistry {

    private final List<String> defaultExcludePatterns = new ArrayList<>();
    private final List<String> excludePatterns = new ArrayList<>();
    private boolean enabled = true;

    public SecurityRegistry() {
        this.defaultExcludePatterns.add("/token/**");
        this.defaultExcludePatterns.add("/auth/**");
        this.defaultExcludePatterns.add("/*/v2/api-docs");
        this.defaultExcludePatterns.add("/actuator/health/**");
    }

    public SecurityRegistry excludePathPatterns(String... patterns) {
        return excludePathPatterns(Arrays.asList(patterns));
    }

    public SecurityRegistry excludePathPatterns(List<String> patterns) {
        this.excludePatterns.addAll(patterns);
        return this;
    }

}
