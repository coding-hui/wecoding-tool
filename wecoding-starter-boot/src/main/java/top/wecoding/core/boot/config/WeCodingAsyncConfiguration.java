package top.wecoding.core.boot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import top.wecoding.core.boot.props.WeCodingAsyncProperties;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 * 异步处理配置中心
 *
 * @author liuyuhui
 * @date 2022/5/15
 * @qq 1515418211
 */
@Slf4j
@EnableAsync
@EnableScheduling
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(WeCodingAsyncProperties.class)
public class WeCodingAsyncConfiguration implements SchedulingConfigurer {

    private final WeCodingAsyncProperties asyncProperties;

    /**
     * 默认使用的cpu密集型线程池，适用于系统中的普通异步处理
     */
    @Bean(name = "threadPoolTaskExecutor", destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(asyncProperties.getCorePoolSize());
        executor.setMaxPoolSize(asyncProperties.getMaxPoolSize());
        executor.setQueueCapacity(asyncProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(asyncProperties.getKeepAliveSeconds());
        executor.setThreadNamePrefix("wecoding-async-executor-");
        executor.setRejectedExecutionHandler((r, exec) -> {
            throw new RejectedExecutionException("线程池已满.");
        });
        executor.initialize();
        return executor;
    }

    /**
     * 默认使用cpu空闲数量来执行任务，适用与大数据量业务优化（如批量数据库处理）
     */
    @Bean(name = "workStealingPool", destroyMethod = "shutdown")
    public ExecutorService workStealingPool() {
        return Executors.newWorkStealingPool();
    }

    /**
     * 周期性异步线程池，防止异步任务丢失
     */
    @Bean(name = "scheduledThreadPool", destroyMethod = "shutdown")
    public ExecutorService scheduledThreadPool() {
        return Executors.newScheduledThreadPool(3);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(scheduledThreadPool());
    }

    public interface ExecutorType {
        String DEFAULT_EXECUTOR = "threadPoolTaskExecutor";
        String WORK_EXECUTOR = "workStealingPool";
        String SCHEDULED_EXECUTOR = "scheduledThreadPool";
    }

}
