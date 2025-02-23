package com.shell.webapplication.auth.service;

import com.shell.webapplication.auth.dto.RegisterUserDto;
import com.shell.webapplication.auth.entity.UserEntity;
import com.shell.webapplication.exception.customexception.UserNotFoundException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.io.IOException;

@Service
@Transactional
@CacheConfig(cacheNames = "user_entity_cache")
public interface UserService {

    public static final String cacheName = "user_entity_cache";

    @CachePut(key = "#result.userId")
    public abstract UserEntity save(RegisterUserDto registerUserDto) throws Exception, IOException, DataIntegrityViolationException;

    @Cacheable(key = "#userId")
    public abstract UserEntity getUser(Long userId) throws UserNotFoundException;
}
