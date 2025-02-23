package com.shell.webapplication.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogoutRequestDto {

    @JsonProperty(value = "accessToken")
    private String accessToken;
    @JsonProperty(value = "refreshToken")
    private String refreshToken;

}
