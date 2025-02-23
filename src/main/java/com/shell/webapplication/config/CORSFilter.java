package com.shell.webapplication.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

//The Filter will be applied for each request
//@WebFilter("/")
//need the class to be implements the filter interface
public class CORSFilter {

//    @Value("client.port")
//    private String clientPort;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//        httpServletResponse.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, clientPort);
//        httpServletResponse.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Authorization, Origin, Accept, X-Requested-With");
//        httpServletResponse.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, PUT, POST, DELETE, OPTIONS");
//        httpServletResponse.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
//        //Continue the request-response
//        chain.doFilter(request, response);
//    }

}
