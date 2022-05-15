package top.wecoding.core.boot.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 异步配置
 *
 * @author liuyuhui
 * @date 2022/5/15
 * @qq 1515418211
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties("wecoding.async")
public class WeCodingAsyncProperties {

    /**
     * 异步核心线程数，默认：2
     */
    private int corePoolSize = 2;

    /**
     * 异步最大线程数，默认：50
     */
    private int maxPoolSize = 50;

    /**
     * 队列容量，默认：10000
     */
    private int queueCapacity = 10000;

    /**
     * 线程存活时间，默认：300
     */
    private int keepAliveSeconds = 300;

}