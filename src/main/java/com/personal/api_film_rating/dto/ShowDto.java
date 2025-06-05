package com.personal.api_film_rating.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ShowDto(
        Long id,
        String title,
        String description,
        LocalDate releaseDate,
        Integer duration,
        String poster,
        String trailer,
        BigDecimal rating,
        ShowStatusDto status,
        LanguageDto language,
        List<GenreDto> genres,
        List<StreamingPlatformDto> streamingPlatforms,
        List<CountryDto> countries,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
