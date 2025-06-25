package com.shell.webapplication.auth.serviceimpl;

import com.shell.webapplication.auth.dto.CustomUserDetails;
import com.shell.webapplication.auth.dto.LoginRequestDto;
import com.shell.webapplication.auth.dto.LoginResponseDto;
import com.shell.webapplication.auth.entity.UserEntity;
import com.shell.webapplication.auth.repository.UserRepository;
import com.shell.webapplication.auth.service.AuthService;
import com.shell.webapplication.auth.service.CustomUserDetailsService;
import com.shell.webapplication.cache.service.CaffeineCacheService;
import com.shell.webapplication.constent.AppConstant;
import com.shell.webapplication.context.UserContext;
import com.shell.webapplication.exception.customexception.TokenExpirationException;
import com.shell.webapplication.exception.customexception.UserNotFoundException;
import com.shell.webapplication.jwt.JwtUtils;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private CaffeineCacheService caffeineCacheService;

    @Override
    public LoginResponseDto login(@NotNull LoginRequestDto loginRequestDto) throws Exception, BadCredentialsException {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUserName(), loginRequestDto.getUserPassword()));
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailsService
                .loadUserByUsername(principal.getUserEmail());
        if (!passwordEncoder.matches(loginRequestDto.getUserPassword(), customUserDetails.getPassword())) {
            throw new BadCredentialsException(AppConstant.INVALID_USER_NAME_OR_PASSWORD);
        }
        UserContext.setLoggedInUserId(customUserDetails.getUser().getUserId());
        String ACCESS_TOKEN = jwtUtils.generateToken(customUserDetails, false);
        String REFRESH_TOKEN = jwtUtils.generateToken(customUserDetails, true);
        return new LoginResponseDto(ACCESS_TOKEN, REFRESH_TOKEN);
    }

    @Override
    public void logout() {
        if (Objects.nonNull(UserContext.getLoggedInUserId())) {
            caffeineCacheService.clearCaffeineCaches();
            UserContext.removeLoggedInUserId();
            SecurityContextHolder.clearContext();
        } else {
            throw new TokenExpirationException();
        }
    }

    @Override
    public UserEntity getLoggedInUser() throws HttpClientErrorException.Unauthorized, UserNotFoundException {
        Objects.requireNonNull(UserContext.getLoggedInUserId(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        System.out.println("get logged in user id stored in thread --> "+UserContext.getLoggedInUserId());
        return userRepository.findById(UserContext.getLoggedInUserId()).orElseThrow(UserNotFoundException::new);
    }

}
