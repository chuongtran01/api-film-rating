package com.personal.api_film_rating.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
