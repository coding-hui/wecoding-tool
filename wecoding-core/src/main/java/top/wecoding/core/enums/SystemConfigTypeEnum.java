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

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyuhui
 * @qq 1515418211
 */
@Getter
public enum SystemConfigTypeEnum {

    /**
     * 系统配置类型
     */
    SYSTEM("system", "系统配置"),

    CUSTOMER("customer", "业务配置");

    private static final Map<String, SystemConfigTypeEnum> KEY_MAP = new HashMap<>();

    static {
        for (SystemConfigTypeEnum item : SystemConfigTypeEnum.values()) {
            KEY_MAP.put(item.getCode(), item);
        }
    }

    private final String code;

    private final String message;

    SystemConfigTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static SystemConfigTypeEnum of(String value) {
        return KEY_MAP.get(value);
    }

}
