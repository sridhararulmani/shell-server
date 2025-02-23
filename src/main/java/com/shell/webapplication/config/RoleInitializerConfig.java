package com.shell.webapplication.config;

import com.shell.webapplication.auth.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializerConfig {

    @Autowired
    private UserRoleService userRoleService;

    @Bean
    public ApplicationRunner initializingUserRoles() {
        return args -> userRoleService.initializeUserRolesByApplicationRunner();
    }

}
