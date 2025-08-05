package com.shell.webapplication.exception.customexception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED.name());
    }
}
