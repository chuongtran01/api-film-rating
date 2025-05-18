package com.personal.api_film_rating.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String displayName;
    private String email;
    private String role;
    private String gender;
    private LocalDate dob;
}
