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
package top.wecoding.core.security.provider;

/**
 * 获取客户端详情接口
 *
 * @author liuyuhui
 * @date 2022
 * @qq 1515418211
 */
public interface ClientDetailsService {

    /**
     * 根据 clientId 获取 Client 详情.
     *
     * @param clientId 客户端id
     * @return ClientDetails
     */
    ClientDetails loadClientByClientId(String clientId);

}
