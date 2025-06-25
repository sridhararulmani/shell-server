package com.shell.webapplication.auth.controller;

import com.shell.webapplication.auth.dto.LoginRequestDto;
import com.shell.webapplication.auth.dto.LoginResponseDto;
import com.shell.webapplication.auth.dto.UserDto;
import com.shell.webapplication.auth.entity.UserEntity;
import com.shell.webapplication.auth.service.AuthService;
import com.shell.webapplication.auth.service.UserService;
import com.shell.webapplication.exception.customexception.UserNotFoundException;
import com.shell.webapplication.utils.app.AppResponse;
import com.shell.webapplication.utils.app.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;
    @Autowired
    AppResponse appResponse;
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<AppResponse> logout() throws Exception {
        System.out.println("Logout Process working");
        AppUtil.setLogOutSuccessResponse(appResponse);
        authService.logout();
        return ResponseEntity.ok(appResponse);
    }

    @GetMapping("/get-login-user")
    public ResponseEntity<UserDto> getUser() throws UserNotFoundException {
        return ResponseEntity.ok(userService.mapToUserDto(authService.getLoggedInUser()));
    }

}