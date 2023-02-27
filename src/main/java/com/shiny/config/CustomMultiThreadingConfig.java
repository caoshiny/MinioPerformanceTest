package com.shiny.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 线程池配置
 */
@Configuration
@ComponentScan("com.shiny")
@EnableAsync
public class CustomMultiThreadingConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 设置线程池的大小
        taskExecutor.setCorePoolSize(50);
        // 线程池所使用的缓冲队列
        taskExecutor.setQueueCapacity(256);
        // 连接池中保留的最大连接数
        taskExecutor.setMaxPoolSize(1024);
        // 线程名前缀
        taskExecutor.setThreadNamePrefix("Application Thread-");
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler(){
        return AsyncConfigurer.super.getAsyncUncaughtExceptionHandler();
    }
}
