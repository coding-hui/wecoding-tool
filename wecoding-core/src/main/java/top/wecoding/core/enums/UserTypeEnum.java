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
package top.wecoding.core.enums;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户类型枚举
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Getter
public enum UserTypeEnum {

    SUPER_ADMIN("1", "管理员"),

    TEACHER("2", "教师"),

    ASSISTANT("3", "助教"),

    STUDENT("5", "学生"),

    COMMON("6", "普通用户");

    private static final Map<String, UserTypeEnum> KEY_MAP = new HashMap<>();

    static {
        for (UserTypeEnum item : UserTypeEnum.values()) {
            KEY_MAP.put(item.getCode(), item);
        }
    }

    private final String code;
    private final String message;

    UserTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static UserTypeEnum of(String userType) {
        return KEY_MAP.get(userType);
    }

    public boolean eq(String userType) {
        UserTypeEnum userTypeEnum = of(userType);
        if (ObjectUtil.isNotNull(userTypeEnum)) {
            return this.code.equals(userTypeEnum.getCode());
        }
        return false;
    }

}
