package com.personal.api_film_rating.mapper;

import org.mapstruct.Mapper;

import com.personal.api_film_rating.dto.CountryDto;
import com.personal.api_film_rating.entity.Country;

@Mapper(componentModel = "spring")
public interface CountryMapper {
  CountryDto toCountryDto(Country country);

  Country toCountry(CountryDto countryDto);
}
