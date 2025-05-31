package com.personal.api_film_rating.dto;

import java.time.LocalDateTime;

public record StreamingPlatformDto(
                String id,
                String name,
                String logo,
                String url,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {

}
