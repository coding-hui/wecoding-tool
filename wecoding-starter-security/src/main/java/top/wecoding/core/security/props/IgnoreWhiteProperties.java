package top.wecoding.core.security.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 放行白名单配置
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Data
@ConfigurationProperties("wecoding.security")
public class IgnoreWhiteProperties {

    private List<String> whites = new ArrayList<>();

}
