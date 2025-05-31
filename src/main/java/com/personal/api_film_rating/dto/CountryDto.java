package com.personal.api_film_rating.dto;

import java.time.LocalDateTime;

public record CountryDto(
        String id,
        String name,
        String flag,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
