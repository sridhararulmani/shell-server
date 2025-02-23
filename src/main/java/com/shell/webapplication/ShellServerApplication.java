package com.shell.webapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableCaching
//Multi Threads
@EnableAsync
//@EntityScan(basePackages = "com.shell.webapplication")
public class ShellServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShellServerApplication.class, args);
    }

}
