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
package top.wecoding.core.security.cache;

import top.wecoding.core.auth.model.LoginUser;
import top.wecoding.core.cache.base.BaseRedisCacheOperator;
import top.wecoding.core.cache.redis.service.RedisService;

/**
 * 登录用户信息缓存
 *
 * @author liuyuhui
 * @date 2022/5/18
 * @qq 1515418211
 */
public class LoginUserCache extends BaseRedisCacheOperator<LoginUser> {

    public static final String LOGIN_USER_CACHE = "login_user:";

    public LoginUserCache(RedisService redisService) {
        super(redisService);
    }

    @Override
    public String getKeyPrefix() {
        return LOGIN_USER_CACHE;
    }

}
