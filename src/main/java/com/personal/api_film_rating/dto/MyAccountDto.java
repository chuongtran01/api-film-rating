package com.personal.api_film_rating.dto;

import java.time.LocalDate;

public record MyAccountDto(
        String displayName,
        LocalDate dob,
        String gender
) {
}
