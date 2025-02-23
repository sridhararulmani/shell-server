package com.shell.webapplication.auth.controller;

import com.shell.webapplication.auth.dto.RegisterUserDto;
import com.shell.webapplication.auth.entity.UserEntity;
import com.shell.webapplication.auth.service.UserService;
import com.shell.webapplication.context.UserContext;
import com.shell.webapplication.exception.customexception.CustomValidationException;
import com.shell.webapplication.exception.customexception.UserNotFoundException;
import com.shell.webapplication.utils.app.AppResponse;
import com.shell.webapplication.utils.app.AppUtil;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AppResponse appResponse;

    @PostMapping(value = "/registerUser", consumes = "multipart/form-data")
    public ResponseEntity<AppResponse> registerNewUser(@ModelAttribute @Valid RegisterUserDto registerUserDto, BindingResult bindingResult)
            throws Exception, IOException, IOFileUploadException, CustomValidationException, MethodArgumentNotValidException {
        if (!bindingResult.hasErrors()) {
            userService.save(registerUserDto);
            AppUtil.setRegisteredSuccessResponse(appResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(appResponse);
        } else {
            throw new CustomValidationException(bindingResult);
        }
    }

    @GetMapping(value = "/getUser")
    public ResponseEntity<UserEntity> getUser() throws UserNotFoundException, HttpClientErrorException.Unauthorized {
        return ResponseEntity.ok(userService.getUser(UserContext.getLoggedInUserId()));
    }

}