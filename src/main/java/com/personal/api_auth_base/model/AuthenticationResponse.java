package com.personal.api_auth_base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private HttpStatus status;
    private String accessToken;
    private User user;
}
