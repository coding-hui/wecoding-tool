package top.wecoding.core.jwt.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 令牌信息
 *
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
    private String token;

    @ApiModelProperty(value = "有效期", notes = "有效时间：单位：秒")
    private long expiresIn;

    @ApiModelProperty(value = "到期时间")
    private LocalDateTime expiration;

}
