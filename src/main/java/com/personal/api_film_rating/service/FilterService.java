package com.personal.api_film_rating.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.personal.api_film_rating.entity.Country;
import com.personal.api_film_rating.entity.Genre;
import com.personal.api_film_rating.entity.Language;
import com.personal.api_film_rating.entity.ShowStatus;
import com.personal.api_film_rating.entity.StreamingPlatform;

public interface FilterService {

  Page<Genre> findGenresPageable(String name, String id, Pageable pageable);

  List<Genre> findAllGenres();

  Genre findGenreById(String id);

  Genre createGenre(Genre genre);

  Genre updateGenre(Genre genre);

  void deleteGenre(String id);

  List<StreamingPlatform> findAllStreamingPlatforms();

  Page<StreamingPlatform> findStreamingPlatformsPageable(String name, String id, Pageable pageable);

  StreamingPlatform findStreamingPlatformById(String id);

  StreamingPlatform createStreamingPlatform(StreamingPlatform streamingPlatform);

  StreamingPlatform updateStreamingPlatform(StreamingPlatform streamingPlatform);

  void deleteStreamingPlatform(String id);

  List<Country> findAllCountries();

  Page<Country> findCountriesPageable(String name, String id, Pageable pageable);

  Country findCountryById(String id);

  Country createCountry(Country country);

  Country updateCountry(Country country);

  void deleteCountry(String id);

  List<ShowStatus> findAllShowStatuses();

  Page<ShowStatus> findShowStatusesPageable(String name, String id, Pageable pageable);

  ShowStatus findShowStatusById(String id);

  ShowStatus createShowStatus(ShowStatus status);

  ShowStatus updateShowStatus(ShowStatus status);

  void deleteShowStatus(String id);

  List<Language> findAllLanguages();

  Page<Language> findLanguagesPageable(String name, String id, Pageable pageable);

  Language findLanguageById(String id);

  Language createLanguage(Language language);

  Language updateLanguage(Language language);

  void deleteLanguage(String id);
}
