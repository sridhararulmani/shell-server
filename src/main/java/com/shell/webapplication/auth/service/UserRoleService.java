package com.shell.webapplication.auth.service;

import com.shell.webapplication.auth.dto.RoleRegisterDto;
import com.shell.webapplication.auth.entity.UserRoleEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@CacheConfig(cacheNames = "user_role_entity_cache")
public interface UserRoleService {

    public static final String cacheName = "user_role_entity_cache";

    @Cacheable(key = "'findAllUserRoles'")
    public abstract List<UserRoleEntity> findAllUserRoles();

    public CompletableFuture<List<UserRoleEntity>> getAllUserRoles();

    @Cacheable(key = "#id")
    public abstract UserRoleEntity findUserRoleByRoleId(Long id);

    //    @Caching(
//            cacheable = @Cacheable(key = "#userRoleEntity.roleId"),
//            evict = @CacheEvict(key = "#userRoleEntity.roleId")
//    )
    @CachePut(key = "#result.roleId")
    public abstract UserRoleEntity save(RoleRegisterDto roleRegisterDto);

//    @CachePut(key = "#userRoleEntities.roleId", unless = "#userRoleEntities == null")
    public abstract void initializeUserRolesByApplicationRunner();

}
