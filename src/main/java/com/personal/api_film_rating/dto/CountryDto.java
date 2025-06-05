package com.personal.api_film_rating.dto;

import java.time.LocalDateTime;

public record CountryDto(
                Integer id,
                String code,
                String name,
                String flag,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {
}
