package com.personal.api_film_rating.mapper;

import org.mapstruct.Mapper;

import com.personal.api_film_rating.dto.StreamingPlatformDto;
import com.personal.api_film_rating.entity.StreamingPlatform;

@Mapper(componentModel = "spring")
public interface StreamingPlatformMapper {
  StreamingPlatformDto toStreamingPlatformDto(StreamingPlatform streamingPlatform);

  StreamingPlatform toStreamingPlatform(StreamingPlatformDto streamingPlatformDto);
}
