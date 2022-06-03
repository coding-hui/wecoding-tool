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

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import top.wecoding.core.constant.SecurityConstants;
import top.wecoding.core.security.provider.ClientDetails;
import top.wecoding.core.security.provider.ClientDetailsService;

/**
 * @author liuyuhui
 * @date 2022
 * @qq 1515418211
 */
@AllArgsConstructor
public class JdbcClientDetailsService implements ClientDetailsService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        try {
            return jdbcTemplate.queryForObject(SecurityConstants.DEFAULT_SELECT_STATEMENT, new BeanPropertyRowMapper<>(BaseClientDetails.class), clientId);
        } catch (Exception ex) {
            return null;
        }
    }

}
