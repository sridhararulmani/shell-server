package com.shell.webapplication.auth.serviceimpl;

import com.shell.webapplication.auth.dto.RoleRegisterDto;
import com.shell.webapplication.auth.entity.UserRoleEntity;
import com.shell.webapplication.auth.repository.UserRoleRepository;
import com.shell.webapplication.auth.service.UserRoleService;
import com.shell.webapplication.constent.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Transactional
@CacheConfig(cacheNames = "user_role_entity_cache")
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private CacheManager cacheManager;

    @Override
    @Cacheable(key = "'findAllUserRoles'")
    public List<UserRoleEntity> findAllUserRoles() {
        return userRoleRepository.findAll();
    }

    public CompletableFuture<List<UserRoleEntity>> getAllUserRoles() {
        return CompletableFuture.completedFuture(findAllUserRoles());
    }

    @Override
    public UserRoleEntity findUserRoleByRoleId(Long id) {
        return userRoleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role Not Founded for the given id :" + id));
    }

    @Override
    public UserRoleEntity save(RoleRegisterDto roleRegisterDto) {
        return userRoleRepository.save(new UserRoleEntity(null, roleRegisterDto.getRole()));
    }

    @Override
    public void initializeUserRolesByApplicationRunner() {
        List<String> storedRoleList = getAllUserRoles().join().stream().map(UserRoleEntity::getRole).collect(Collectors.toList());
        List<UserRoleEntity> rolesToBeCreateList = new ArrayList<>();
        List<UserRoleEntity> userRoleEntities = null;
        List<String> applicationRoles = List.of(RolesEnum.USER.name(),
                RolesEnum.ADMIN.name(),
                RolesEnum.MANAGER.name(),
                RolesEnum.SELLER.name(),
                RolesEnum.OWNER.name(),
                RolesEnum.SUPER_ADMIN.name());
        for (String role : applicationRoles) {
            if (!storedRoleList.contains(role)) {
                rolesToBeCreateList.add(new UserRoleEntity(null, role));
            }
        }
        if (!rolesToBeCreateList.isEmpty()) {
            rolesToBeCreateList = userRoleRepository.saveAll(rolesToBeCreateList);
        }
        /** When a method returns a list of objects always prepare this method of cachePut,
         *  Because here we are returning a list so the cachePut annotation will assume,
         *  if we declare the key as a result.id it will assume,
         *  the method return object has a property called id so we will face exception **/
        rolesToBeCreateList.forEach(newlyCreatedUserRoleEntity -> {
            cacheManager.getCache(cacheName).put(newlyCreatedUserRoleEntity.getRole(), newlyCreatedUserRoleEntity);
        });
    }

}
