package top.wecoding.core.security.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuyuhui
 * @date 2021/09/09
 * @qq 1515418211
 */
@Data
@Configuration
@ConfigurationProperties(prefix = SocialProperties.PREFIX)
public class SocialProperties {

    /**
     * 前缀
     */
    public static final String PREFIX = "security.social";

    private String url;

    private Boolean autoRegister;

}
