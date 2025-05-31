package com.personal.api_film_rating.mapper;

import org.mapstruct.Mapper;

import com.personal.api_film_rating.dto.GenreDto;
import com.personal.api_film_rating.entity.Genre;

@Mapper(componentModel = "spring")
public interface GenreMapper {
  GenreDto toGenreDto(Genre genre);

  Genre toGenre(GenreDto genreDto);
}
