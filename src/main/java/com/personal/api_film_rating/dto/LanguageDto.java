package com.personal.api_film_rating.dto;

import java.time.LocalDateTime;

public record LanguageDto(
        String id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

}
