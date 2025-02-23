package com.shell.webapplication.auth.service;

import com.shell.webapplication.auth.dto.LoginRequestDto;
import com.shell.webapplication.auth.dto.LoginResponseDto;
import com.shell.webapplication.auth.entity.UserEntity;
import com.shell.webapplication.exception.customexception.UserNotFoundException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@CacheConfig(cacheNames = "auth_cache")
public interface AuthService {

    public static final String cacheName = "auth_cache";

    public abstract LoginResponseDto login(LoginRequestDto loginRequestDto) throws Exception;

    public abstract void logout() throws Exception;

    @Cacheable(key = "'getLoggedInUser'")
    public abstract UserEntity getLoggedInUser() throws UserNotFoundException;

}
