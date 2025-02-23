package com.shell.webapplication.config;

import com.shell.webapplication.auth.service.CustomUserDetailsService;
import com.shell.webapplication.constent.RolesEnum;
import com.shell.webapplication.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Value("${app.url}")
    private String appUrl;
    @Value("${client.port}")
    private String clientPort;
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        System.out.println("Security Filter Chain Executing...!");
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
//                .cors(Customizer.withDefaults()) // default-ly it will take the cors config method by the same name of the type CorsConfigurationSource
//                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
//                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers(appUrl + "/auth/login").anonymous();
                    registry.requestMatchers(appUrl + "/user/registerUser").permitAll();
                    registry.requestMatchers(appUrl + "/role/**").hasAnyRole(
                            RolesEnum.SUPER_ADMIN.name()
                            , RolesEnum.MANAGER.name()
                            , RolesEnum.OWNER.name());
                    registry.anyRequest().authenticated();
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        System.out.println("Cors Configuration is Executing...!");
//        System.out.println(clientPort);
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4000"));
//        corsConfiguration.setAllowedMethods(Arrays.asList(
//                HttpMethod.GET.name(),
//                HttpMethod.POST.name(),
//                HttpMethod.PUT.name(),
//                HttpMethod.DELETE.name(),
//                HttpMethod.OPTIONS.name()
//                ));
//        corsConfiguration.setAllowedHeaders(List.of(
//                HttpHeaders.CONTENT_TYPE,
//                HttpHeaders.AUTHORIZATION
//                ));
//        corsConfiguration.setMaxAge(3600l);
//        corsConfiguration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//        System.out.println("Cors Configuration is Ending...!");
//        return urlBasedCorsConfigurationSource;
//    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        System.out.println("Authentication Manager is Executing...!");
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
