package com.personal.api_film_rating.mapper;

import org.mapstruct.Mapper;

import com.personal.api_film_rating.dto.ShowTypeDto;
import com.personal.api_film_rating.entity.ShowType;

@Mapper(componentModel = "spring")
public interface ShowTypeMapper {
  ShowTypeDto toShowTypeDto(ShowType showType);

  ShowType toShowType(ShowTypeDto showTypeDto);
}
