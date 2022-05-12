package top.wecoding.core.jwt.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.wecoding.core.constant.SecurityConstants;

/**
 * @author liuyuhui
 * @date 2022
 * @qq 1515418211
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "令牌信息")
public class TokenInfo {

    @ApiModelProperty(value = "令牌")
    private String accessToken;

    @ApiModelProperty(value = "令牌类型")
    private String tokenType;

    @ApiModelProperty(value = "刷新令牌")
    private String refreshToken;

    @ApiModelProperty(value = "用户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "账号名")
    private String account;

    @ApiModelProperty(value = "过期时间")
    private long expiresIn;

    @ApiModelProperty(value = "许可证")
    private String license = SecurityConstants.PROJECT_LICENSE;

}
