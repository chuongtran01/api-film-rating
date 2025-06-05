package com.personal.api_film_rating.dto;

import java.util.List;

public record FilterDto(
        List<GenreDto> genres,
        List<StreamingPlatformDto> streamingPlatforms,
        List<CountryDto> countries,
        List<ShowStatusDto> showStatuses,
        List<LanguageDto> languages,
        List<ShowTypeDto> showTypes) {

}