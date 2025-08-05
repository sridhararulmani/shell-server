package com.shell.webapplication.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class RefreshTokensDTO {

    private String accessToken;
    private String refreshToken;

}
