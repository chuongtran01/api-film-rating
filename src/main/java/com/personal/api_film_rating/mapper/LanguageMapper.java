package com.personal.api_film_rating.mapper;

import org.mapstruct.Mapper;

import com.personal.api_film_rating.dto.LanguageDto;
import com.personal.api_film_rating.entity.Language;

@Mapper(componentModel = "spring")
public interface LanguageMapper {
  LanguageDto toLanguageDto(Language language);

  Language toLanguage(LanguageDto languageDto);
}
