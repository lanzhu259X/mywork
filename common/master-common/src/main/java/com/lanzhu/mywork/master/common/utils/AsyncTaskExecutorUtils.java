package com.lanzhu.mywork.master.common.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * description: 异步执行器
 *
 * @author lanzhu259X
 * @date 2018-12-13
 */
@Log4j2
public class AsyncTaskExecutorUtils {

    private static volatile ThreadPoolTaskExecutor taskExecutor = null;
    private static volatile int queueCapacity = 50000;

    public static void setTaskExecutor(int queueCapacity, ThreadPoolTaskExecutor taskExecutor) {
        AsyncTaskExecutorUtils.taskExecutor = taskExecutor;
        AsyncTaskExecutorUtils.queueCapacity = queueCapacity;
    }

    private static synchronized void initDefaultTaskPool() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(50);
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setQueueCapacity(queueCapacity);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.initialize();
        AsyncTaskExecutorUtils.setTaskExecutor(queueCapacity, taskExecutor);
    }

    /**
     * 执行异步任务
     */
    public static void execute(Runnable task){
        if (taskExecutor == null) {
            initDefaultTaskPool();
        }
        try {
            taskExecutor.execute(task);
            logQueueSize();
        } catch (Exception e) {
            log.error("异步任务启动失败！",e);
        }
    }

    public static <V> Future<V> submit(Callable<V> callable){
        Future<V> future = taskExecutor.submit(callable);
        logQueueSize();
        return future;
    }


    private static void logQueueSize(){
        int queueSize = taskExecutor.getThreadPoolExecutor().getQueue().size();
        if (queueSize>0 && queueSize%100==0){
            log.info("AsyncTaskExecutor 队列长度:{}，队列已使用{}%",queueSize,(100f*queueSize)/queueCapacity);
        }
    }
}
