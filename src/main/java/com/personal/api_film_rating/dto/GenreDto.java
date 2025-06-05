package com.personal.api_film_rating.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public record GenreDto(
                Integer id,
                @NotBlank(message = "Code is required") String code,
                @NotBlank(message = "Name is required") String name,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {

}
