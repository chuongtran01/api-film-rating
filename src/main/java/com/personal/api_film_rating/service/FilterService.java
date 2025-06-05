package com.personal.api_film_rating.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.personal.api_film_rating.entity.Country;
import com.personal.api_film_rating.entity.Genre;
import com.personal.api_film_rating.entity.Language;
import com.personal.api_film_rating.entity.ShowStatus;
import com.personal.api_film_rating.entity.ShowType;
import com.personal.api_film_rating.entity.StreamingPlatform;

public interface FilterService {

  Page<Genre> findGenresPageable(String name, String code, Pageable pageable);

  List<Genre> findAllGenres();

  Genre findGenreById(Integer id);

  Genre createGenre(Genre genre);

  Genre updateGenre(Genre genre);

  void deleteGenre(Integer id);

  List<StreamingPlatform> findAllStreamingPlatforms();

  Page<StreamingPlatform> findStreamingPlatformsPageable(String name, String code, Pageable pageable);

  StreamingPlatform findStreamingPlatformById(Integer id);

  StreamingPlatform createStreamingPlatform(StreamingPlatform streamingPlatform);

  StreamingPlatform updateStreamingPlatform(StreamingPlatform streamingPlatform);

  void deleteStreamingPlatform(Integer id);

  List<Country> findAllCountries();

  Page<Country> findCountriesPageable(String name, String code, Pageable pageable);

  Country findCountryById(Integer id);

  Country createCountry(Country country);

  Country updateCountry(Country country);

  void deleteCountry(Integer id);

  List<ShowStatus> findAllShowStatuses();

  Page<ShowStatus> findShowStatusesPageable(String name, String code, Pageable pageable);

  ShowStatus findShowStatusById(Integer id);

  ShowStatus createShowStatus(ShowStatus status);

  ShowStatus updateShowStatus(ShowStatus status);

  void deleteShowStatus(Integer id);

  List<Language> findAllLanguages();

  Page<Language> findLanguagesPageable(String name, String code, Pageable pageable);

  Language findLanguageById(Integer id);

  Language createLanguage(Language language);

  Language updateLanguage(Language language);

  void deleteLanguage(Integer id);

  List<ShowType> findAllShowTypes();

  Page<ShowType> findShowTypesPageable(String name, String code, Pageable pageable);

  ShowType findShowTypeById(Integer id);

  ShowType createShowType(ShowType showType);

  ShowType updateShowType(ShowType showType);

  void deleteShowType(Integer id);
}
