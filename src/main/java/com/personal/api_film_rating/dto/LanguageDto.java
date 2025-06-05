package com.personal.api_film_rating.dto;

import java.time.LocalDateTime;

public record LanguageDto(
                Integer id,
                String code,
                String name,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {

}
