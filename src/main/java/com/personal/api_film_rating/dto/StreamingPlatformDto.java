package com.personal.api_film_rating.dto;

import java.time.LocalDateTime;

public record StreamingPlatformDto(
                Integer id,
                String code,
                String name,
                String logo,
                String url,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {

}
