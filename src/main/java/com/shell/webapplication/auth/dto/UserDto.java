package com.shell.webapplication.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {

    private String userName;
    private String userEmail;
    private Long mobileNumber;
    private byte[] profileImage;
    private String profileImageType;
    private List<String> userRoles;

}
