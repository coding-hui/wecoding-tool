package top.wecoding.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * 登录参数
 *
 * @author liuyuhui
 * @date 2022
 * @qq 1515418211
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LoginParamDTO", description = "登录参数")
public class LoginRequest {

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * password: 账号密码
     * captcha: 验证码
     */
    @ApiModelProperty(value = "授权类型", example = "password", allowableValues = "captcha,password")
    @NotEmpty(message = "授权类型不能为空")
    private String grantType;

}
