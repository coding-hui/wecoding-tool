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
package top.wecoding.core.auth.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.wecoding.core.constant.SecurityConstants;

import java.time.LocalDateTime;

/**
 * 认证成功信息
 *
 * @author liuyuhui
 * @date 2022/5/11
 * @qq 1515418211
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "认证信息")
public class AuthInfo {

    @ApiModelProperty(value = "会话唯一标识")
    private String uuid;

    @ApiModelProperty(value = "令牌")
    private String accessToken;

    @ApiModelProperty(value = "令牌类型")
    private String tokenType;

    @ApiModelProperty(value = "刷新令牌")
    private String refreshToken;

    @ApiModelProperty(value = "用户Id")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String realName;

    @ApiModelProperty(value = "账号名")
    private String account;

    @ApiModelProperty(value = "到期时间")
    private LocalDateTime expiration;

    @ApiModelProperty(value = "有效期")
    private Long expireMillis;

    @ApiModelProperty(value = "客户端Id")
    private String clientId;

    @ApiModelProperty(value = "许可证")
    private String license = SecurityConstants.PROJECT_LICENSE;

}
