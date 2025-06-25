package com.shell.webapplication.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto implements Serializable {

    private String accessToken;
    private String refreshToken;

}
