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
package top.wecoding.core.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectionException;
import top.wecoding.core.auth.util.AuthUtil;
import top.wecoding.core.constant.StrPool;

import java.util.Date;

/**
 * 自定义sql字段填充器，自动填充创建修改相关字段
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Slf4j
public class CustomMetaObjectHandler implements MetaObjectHandler {

    private static final String CREATE_USER = "createUser";

    private static final String CREATE_TIME = "createTime";

    private static final String UPDATE_USER = "updateUser";

    private static final String UPDATE_TIME = "updateTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            // 设置createUser(BaseEntity)
            this.strictInsertFill(metaObject, CREATE_USER, this::getLoginUserName, String.class);

            // 设置createTime(BaseEntity)
            this.strictInsertFill(metaObject, CREATE_TIME, Date::new, Date.class);
        } catch (ReflectionException e) {
            log.warn(">>> CustomMetaObjectHandler 处理过程中无相关字段，不做处理");
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            // 设置createUser(BaseEntity)
            this.strictUpdateFill(metaObject, UPDATE_USER, this::getLoginUserName, String.class);

            // 设置createTime(BaseEntity)
            this.strictUpdateFill(metaObject, UPDATE_TIME, Date::new, Date.class);
        } catch (ReflectionException e) {
            log.warn(">>> CustomMetaObjectHandler 处理过程中无相关字段，不做处理");
        }
    }

    private String getLoginUserName() {
        try {
            return AuthUtil.getAccount();
        } catch (Exception e) {
            log.error(">>> 获取登录用户失败，具体信息为：{}", e.getLocalizedMessage());
        }
        return StrPool.HYPHEN;
    }

}