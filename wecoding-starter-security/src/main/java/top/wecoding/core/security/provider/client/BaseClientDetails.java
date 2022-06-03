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
package top.wecoding.core.security.provider.client;

import lombok.Data;
import top.wecoding.core.security.provider.ClientDetails;

/**
 * @author liuyuhui
 * @date 2022
 * @qq 1515418211
 */
@Data
public class BaseClientDetails implements ClientDetails {

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 令牌过期秒数
     */
    private Long accessTokenValiditySeconds;

    /**
     * 刷新令牌过期秒数
     */
    private Long refreshTokenValiditySeconds;

}
