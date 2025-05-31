package com.personal.api_film_rating.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record UpdateUserDto(
        String displayName,
        @Email(message = "Email is not valid") String email,
        @Pattern(regexp = "^(ROLE_ADMIN|ROLE_MODERATOR|ROLE_USER)$", message = "Role is not valid") String role,
        String gender,
        LocalDate dob,
        Boolean active) {
}
