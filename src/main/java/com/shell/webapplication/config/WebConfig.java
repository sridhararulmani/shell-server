package com.shell.webapplication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * value annotation will give the value in runtime only not in
     * compaile time this mvc config or cors configuration will load
     * nad read by spring security when the application is compailing itself
     */
    @Value("${client.port}")
    private String clientPort;
    @Value("${app.url}")
    private String appUrl;

    @Bean
    public WebMvcConfigurer crosConfigurer() {
        System.out.println("Web Mvc Configuration is Started here...!");
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(clientPort)
                        .allowedMethods(
                                HttpMethod.GET.name(),
                                HttpMethod.POST.name(),
                                HttpMethod.PUT.name(),
                                HttpMethod.DELETE.name()
                        )
                        .allowedHeaders(
                                HttpHeaders.CONTENT_TYPE,
                                HttpHeaders.AUTHORIZATION
                        )
                        .allowCredentials(true);
            }
        };
    }

    //To Add an common URL before all the RestControllers
    @Override
    public void configurePathMatch(PathMatchConfigurer pathMatchConfigurer) {
        pathMatchConfigurer.addPathPrefix(appUrl, clazz -> clazz.isAnnotationPresent(RestController.class));
    }

}
