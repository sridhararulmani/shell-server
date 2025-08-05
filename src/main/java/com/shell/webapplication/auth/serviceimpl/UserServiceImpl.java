package com.shell.webapplication.auth.serviceimpl;

import com.shell.webapplication.auth.dto.RefreshTokensDTO;
import com.shell.webapplication.auth.dto.RegisterUserDto;
import com.shell.webapplication.auth.dto.RoleDto;
import com.shell.webapplication.auth.dto.UserDto;
import com.shell.webapplication.auth.entity.UserEntity;
import com.shell.webapplication.auth.entity.UserRoleEntity;
import com.shell.webapplication.auth.repository.UserRepository;
import com.shell.webapplication.auth.repository.UserRoleRepository;
import com.shell.webapplication.auth.service.UserService;
import com.shell.webapplication.constent.AppThreadConstant;
import com.shell.webapplication.constent.RolesEnum;
import com.shell.webapplication.exception.customexception.UserNotFoundException;
import com.shell.webapplication.utils.app.DefaultProfileImageGenerator;
import com.shell.webapplication.utils.app.ImageUtils;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Service
@Transactional
@CacheConfig(cacheNames = "user_entity_cache")
public class UserServiceImpl implements UserService {

    public static final String cacheName = "user_entity_cache";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ImageUtils imageUtils;
    @Autowired
    private DefaultProfileImageGenerator defaultProfileImageGenerator;
    @Autowired
    private Executor securityExecutor;

    @Override
    public UserEntity save(@NotNull RegisterUserDto registerUserDto) throws Exception, IOException, DataIntegrityViolationException {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(registerUserDto.getUserName());
        userEntity.setUserEmail(registerUserDto.getUserEmail());
        userEntity.setUserPassword(passwordEncoder.encode(registerUserDto.getUserPassword()));
        if (Objects.nonNull(registerUserDto.getMobileNumber())) {
            userEntity.setMobileNumber(Long.parseLong(registerUserDto.getMobileNumber()));
        }
        if (Objects.isNull(registerUserDto.getUserProfileImage())) {
            byte[] defaultProfileImage = defaultProfileImageGenerator.generateProfileImage(registerUserDto.getUserName());
            userEntity.setProfileImage(defaultProfileImage);
            userEntity.setProfileImageType(MediaType.IMAGE_PNG_VALUE);
        } else {
            userEntity.setProfileImageType(imageUtils.getImageTypeByHeader(registerUserDto.getUserProfileImage()));
            userEntity.setProfileImage(registerUserDto.getUserProfileImage().getBytes());
        }
        userEntity.setUserRoles(userRoleRepository.findAllByRoles(Arrays.asList(RolesEnum.USER.name())));
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity getUser(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserDto mapToUserDto(UserEntity user) {
        return new UserDto(user.getUserName(),
                user.getUserEmail(),
                user.getMobileNumber(),
                user.getProfileImage(),
                user.getProfileImageType(),
                user.getUserRoles().stream().map(UserRoleEntity::getRole).collect(Collectors.toList()));
    }

    @Override
    public RefreshTokensDTO refreshTokens(RefreshTokensDTO dto) {
        return new RefreshTokensDTO();
    }

}
