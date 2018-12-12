package com.lanzhu.mywork.master.config;

import com.lanzhu.mywork.master.utils.AsyncTaskExecutorUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-09-22
 */
@Configuration
@EnableScheduling
public class TaskConfig {

    @Bean
    @Primary
    public TaskExecutor createTaskPool() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(50);
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setQueueCapacity(50000);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.initialize();
        AsyncTaskExecutorUtils.setTaskExecutor(50000, taskExecutor);
        return taskExecutor;
    }

    @Bean
    @Primary
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(1000);
        return taskScheduler;
    }

}
