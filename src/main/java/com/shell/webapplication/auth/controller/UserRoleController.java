package com.shell.webapplication.auth.controller;

import com.shell.webapplication.auth.dto.RoleRegisterDto;
import com.shell.webapplication.auth.service.UserRoleService;
import com.shell.webapplication.utils.app.AppResponse;
import com.shell.webapplication.utils.app.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/role")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private AppResponse appResponse;

    @PostMapping("/register")
    public ResponseEntity<AppResponse> createNewRole(@RequestBody RoleRegisterDto roleRegisterDto) {
        userRoleService.save(roleRegisterDto);
        AppUtil.setSuccessResponse(appResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(appResponse);
    }

}
