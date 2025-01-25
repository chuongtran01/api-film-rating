package com.personal.api_auth_base.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LogoutDto {
    @NotBlank
    private String accessToken;
}
