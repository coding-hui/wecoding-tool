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
package top.wecoding.core.constant;

/**
 * @author liuyuhui
 * @qq 1515418211
 */
public interface TokenConstant {

    /**
     * 令牌自定义标识
     */
    String AUTHENTICATION = "WeCoding-Auth";

    /**
     * 令牌前缀
     */
    String PREFIX = "Bearer ";

    /**
     * 令牌类型
     */
    String TOKEN_TYPE = "token_type";

    /**
     * 访问令牌
     */
    String ACCESS_TOKEN = "access_token";

    /**
     * 刷新令牌
     */
    String REFRESH_TOKEN = "refresh_token";

    /**
     * 令牌秘钥
     */
    String SING_KEY = "U2FsdGVkX1/7rSqZDNzEAFH9plu0RVzYjSGe3d+/LA2Tq3rvzmC1fA==";

}
