package com.personal.api_film_rating.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshAccessTokenDto {
    @NotBlank
    private String refreshToken;
}
