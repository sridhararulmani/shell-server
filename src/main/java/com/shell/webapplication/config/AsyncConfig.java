package com.shell.webapplication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setQueueCapacity(25);
        threadPoolTaskExecutor.setThreadNamePrefix("AsyncSecurityThread-");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

//    @Bean(name = AppThreadConstant.SECURITY_ASYNC_EXECUTOR)
//    public Executor taskExecutor(){
//        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
//        threadPoolTaskExecutor.setCorePoolSize(5);
//        threadPoolTaskExecutor.setMaxPoolSize(10);
//        threadPoolTaskExecutor.setQueueCapacity(25);
//        threadPoolTaskExecutor.setThreadNamePrefix("AsyncSecurityThread-");
//        threadPoolTaskExecutor.initialize();
////        return threadPoolTaskExecutor;
//
//        // To Probagate automatically the securityContext to async threads.
//        // why means when request to the other thread the authentication will store in another thread to map or set the authentication on all the threads.
//        return new DelegatingSecurityContextAsyncTaskExecutor(threadPoolTaskExecutor);
//    }

}
