package top.wecoding.core.jwt.props;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import top.wecoding.core.constant.TokenConstant;

/**
 * @author liuyuhui
 * @date 2022/5/13
 * @qq 1515418211
 */
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = JwtProperties.PREFIX)
public class JwtProperties {

    public static final String PREFIX = "wecoding.token";

    /**
     * 过期时间 2h 单位（秒）
     */
    private Long expire = 7200L;

    /**
     * 刷新Token的过期时间 8h 单位（秒）
     */
    private Long refreshExpire = 28800L;

    /**
     * token签名
     */
    private String signKey = TokenConstant.SING_KEY;

}
