package com.shell.webapplication.exception.handler;

import com.shell.webapplication.constent.AppConstant;
import com.shell.webapplication.exception.customexception.CustomValidationException;
import com.shell.webapplication.exception.customexception.InvalidTokenException;
import com.shell.webapplication.exception.customexception.TokenExpirationException;
import com.shell.webapplication.exception.customexception.UserNotFoundException;
import com.shell.webapplication.utils.app.AppResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.cache.CacheException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalCustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppResponse> handleException(Exception exception, HttpServletRequest httpServletRequest) {
        AppResponse appResponse = new AppResponse();
        appResponse.setPath(httpServletRequest.getRequestURI());
        appResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        appResponse.setMessage(exception.getMessage());
        appResponse.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<AppResponse>(appResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<AppResponse> handleIOException(IOException ioException, HttpServletRequest httpServletRequest) {
        AppResponse appResponse = new AppResponse();
        appResponse.setPath(httpServletRequest.getRequestURI());
        appResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        appResponse.setMessage(ioException.getMessage());
        appResponse.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<AppResponse>(appResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TokenExpirationException.class)
    public ResponseEntity<AppResponse> handleTokenExpirationException(TokenExpirationException tokenExpirationException, HttpServletRequest httpServletRequest) {
        AppResponse appResponse = new AppResponse(
                httpServletRequest.getRequestURI(),
                HttpStatus.FORBIDDEN.value(),
                AppConstant.ACCESS_TOKEN_EXPIRED,
                LocalDateTime.now()
        );
        return new ResponseEntity<AppResponse>(appResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<AppResponse> jwtTokenExpirationException(JwtException jwtException, HttpServletRequest httpServletRequest) {
        AppResponse appResponse = new AppResponse(
                httpServletRequest.getRequestURI(),
                HttpStatus.FORBIDDEN.value(),
                jwtException.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<AppResponse>(appResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<?> handleInvalidTokenException(InvalidTokenException invalidTokenException, HttpServletRequest httpServletRequest) {
        AppResponse appResponse = new AppResponse(
                httpServletRequest.getRequestURI(),
                HttpStatus.UNAUTHORIZED.value(),
                AppConstant.INVALID_TOKEN,
                LocalDateTime.now()
        );
        return new ResponseEntity<AppResponse>(appResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException userNotFoundException, HttpServletRequest httpServletRequest) {
        AppResponse appResponse = new AppResponse(
                httpServletRequest.getRequestURI(),
                HttpStatus.NOT_FOUND.value(),
                AppConstant.USER_NOT_FOUND,
                LocalDateTime.now()
        );
        return new ResponseEntity<AppResponse>(appResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<AppResponse> inSufficientAuthenticationException(InsufficientAuthenticationException insufficientAuthenticationException, HttpServletRequest httpServletRequest) {
        AppResponse appResponse = new AppResponse(
                httpServletRequest.getRequestURI(),
                HttpStatus.UNAUTHORIZED.value(),
                insufficientAuthenticationException.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<AppResponse>(appResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsException(BadCredentialsException badCredentialsException, HttpServletRequest httpServletRequest) {
        AppResponse appResponse = new AppResponse(
                httpServletRequest.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                badCredentialsException.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<AppResponse>(appResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<AppResponse> forbiddenException(HttpClientErrorException.Forbidden forbidden, HttpServletRequest httpServletRequest) {
        AppResponse appResponse = new AppResponse(
                httpServletRequest.getRequestURI(),
                HttpStatus.FORBIDDEN.value(),
                forbidden.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<AppResponse>(appResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnknownError.class)
    public ResponseEntity<AppResponse> unknownException(UnknownError unknownError, HttpServletRequest httpServletRequest) {
        AppResponse appResponse = new AppResponse(
                httpServletRequest.getRequestURI(),
                HttpStatus.SWITCHING_PROTOCOLS.value(),
                unknownError.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<AppResponse>(appResponse, HttpStatus.SWITCHING_PROTOCOLS);
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<AppResponse> unAuthorizedException(HttpClientErrorException.Unauthorized unauthorized, HttpServletRequest httpServletRequest) {
        AppResponse appResponse = new AppResponse(
                httpServletRequest.getRequestURI(),
                HttpStatus.UNAUTHORIZED.value(),
                unauthorized.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<AppResponse>(appResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CacheException.class)
    public ResponseEntity<AppResponse> cacheException(CacheException cacheException, HttpServletRequest httpServletRequest) {
        AppResponse appResponse = new AppResponse(
                httpServletRequest.getRequestURI(),
                HttpStatus.SWITCHING_PROTOCOLS.value(),
                cacheException.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<AppResponse>(appResponse, HttpStatus.SWITCHING_PROTOCOLS);
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<Map<String, String>> customValidationException(CustomValidationException customValidationException) {
        Map<String, String> error = new HashMap<>();
        for (FieldError fieldError : customValidationException.getBindingResult().getFieldErrors()) {
            error.put(fieldError.getField(), fieldError.getDefaultMessage());
            System.out.println(fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> error = new HashMap<>();
        for (FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
            error.put(fieldError.getField(), fieldError.getDefaultMessage());
            System.out.println(fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> constraintViolationException(ConstraintViolationException constraintViolationException) {
        Map<String, String> error = new HashMap<>();
        error.put(constraintViolationException.getConstraintName(), constraintViolationException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<AppResponse> dataIntegrityViolationException(DataIntegrityViolationException dataIntegrityViolationException, HttpServletRequest httpServletRequest) {
        AppResponse appResponse = new AppResponse(
                httpServletRequest.getRequestURI(),
                HttpStatus.CONFLICT.value(),
                dataIntegrityViolationException.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<AppResponse>(appResponse, HttpStatus.CONFLICT);
    }

}
