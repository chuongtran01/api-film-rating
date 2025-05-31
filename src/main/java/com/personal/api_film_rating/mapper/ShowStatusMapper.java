package com.personal.api_film_rating.mapper;

import org.mapstruct.Mapper;

import com.personal.api_film_rating.dto.ShowStatusDto;
import com.personal.api_film_rating.entity.ShowStatus;

@Mapper(componentModel = "spring")
public interface ShowStatusMapper {
  ShowStatusDto toStatusDto(ShowStatus status);

  ShowStatus toStatus(ShowStatusDto statusDto);
}
