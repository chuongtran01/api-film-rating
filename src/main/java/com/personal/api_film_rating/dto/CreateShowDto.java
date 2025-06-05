package com.personal.api_film_rating.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record CreateShowDto(
                @NotBlank(message = "Title is required") String title,
                String description,
                String showTypeId,
                LocalDate releaseDate,
                @NotBlank(message = "Type is required") String type,
                Integer duration) {

}