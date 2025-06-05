package com.personal.api_film_rating.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.personal.api_film_rating.entity.Country;
import com.personal.api_film_rating.entity.Genre;
import com.personal.api_film_rating.entity.Language;
import com.personal.api_film_rating.entity.ShowStatus;
import com.personal.api_film_rating.entity.ShowType;
import com.personal.api_film_rating.entity.StreamingPlatform;
import com.personal.api_film_rating.repository.CountryRepository;
import com.personal.api_film_rating.repository.GenreRepository;
import com.personal.api_film_rating.repository.LanguageRepository;
import com.personal.api_film_rating.repository.ShowStatusRepository;
import com.personal.api_film_rating.repository.ShowTypeRepository;
import com.personal.api_film_rating.repository.StreamingPlatformRepository;
import com.personal.api_film_rating.specifications.BaseFilterSpecifications;

@Service
public class FilterServiceImpl implements FilterService {
  private GenreRepository genreRepository;
  private StreamingPlatformRepository streamingPlatformRepository;
  private CountryRepository countryRepository;
  private ShowStatusRepository statusRepository;
  private LanguageRepository languageRepository;
  private ShowTypeRepository showTypeRepository;

  FilterServiceImpl(GenreRepository genreRepository, StreamingPlatformRepository streamingPlatformRepository,
      CountryRepository countryRepository, ShowStatusRepository statusRepository,
      LanguageRepository languageRepository, ShowTypeRepository showTypeRepository) {
    this.genreRepository = genreRepository;
    this.streamingPlatformRepository = streamingPlatformRepository;
    this.countryRepository = countryRepository;
    this.statusRepository = statusRepository;
    this.languageRepository = languageRepository;
    this.showTypeRepository = showTypeRepository;
  }

  /**
   * Find genres pageable
   * 
   * @param name
   * @param id
   * @param pageable
   * @return Page<Genre>
   */
  @Override
  public Page<Genre> findGenresPageable(String name, String id, Pageable pageable) {
    Specification<Genre> spec = BaseFilterSpecifications.combine(
        BaseFilterSpecifications.hasName(name),
        BaseFilterSpecifications.hasId(id));
    return genreRepository.findAll(spec, pageable);
  }

  /**
   * Find all genres
   * 
   * @return List<Genre>
   */
  @Override
  public List<Genre> findAllGenres() {
    return genreRepository.findAll();
  }

  /**
   * Find a genre by id
   * 
   * @param id
   * @return Genre
   */
  @Override
  public Genre findGenreById(Integer id) {
    return genreRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Genre not found"));
  }

  /**
   * Create a genre
   * 
   * @param genre
   * @return Genre
   */
  @Override
  public Genre createGenre(Genre genre) {
    Genre existingGenre = genreRepository.findByCode(genre.getCode())
        .orElse(null);
    if (existingGenre != null) {
      throw new RuntimeException("Genre with code " + genre.getCode() + " already exists");
    }
    return genreRepository.save(genre);
  }

  /**
   * Update a genre
   * 
   * @param genre
   * @return Genre
   */
  @Override
  public Genre updateGenre(Genre genre) {
    Genre existingGenre = genreRepository.findById(genre.getId())
        .orElse(null);
    if (existingGenre == null) {
      throw new RuntimeException("Genre not found");
    }
    return genreRepository.save(genre);
  }

  /**
   * Delete a genre
   * 
   * @param id
   */
  @Override
  public void deleteGenre(Integer id) {
    Genre genre = genreRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Genre not found"));
    genreRepository.delete(genre);
  }

  /**
   * Find all streaming platforms
   * 
   * @return List<StreamingPlatform>
   */
  @Override
  public List<StreamingPlatform> findAllStreamingPlatforms() {
    return streamingPlatformRepository.findAll();
  }

  /**
   * Find streaming platforms pageable
   * 
   * @param name
   * @param id
   * @param pageable
   * @return Page<StreamingPlatform>
   */
  @Override
  public Page<StreamingPlatform> findStreamingPlatformsPageable(String name, String id, Pageable pageable) {
    Specification<StreamingPlatform> spec = BaseFilterSpecifications.combine(
        BaseFilterSpecifications.hasName(name),
        BaseFilterSpecifications.hasId(id));
    return streamingPlatformRepository.findAll(spec, pageable);
  }

  /**
   * Find a streaming platform by id
   * 
   * @param id
   * @return StreamingPlatform
   */
  @Override
  public StreamingPlatform findStreamingPlatformById(Integer id) {
    return streamingPlatformRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Streaming platform not found"));
  }

  /**
   * Create a streaming platform
   * 
   * @param streamingPlatform
   * @return StreamingPlatform
   */
  @Override
  public StreamingPlatform createStreamingPlatform(StreamingPlatform streamingPlatform) {
    StreamingPlatform existingStreamingPlatform = streamingPlatformRepository.findByCode(streamingPlatform.getCode())
        .orElse(null);
    if (existingStreamingPlatform != null) {
      throw new RuntimeException("Streaming platform with code " + streamingPlatform.getCode() + " already exists");
    }
    return streamingPlatformRepository.save(streamingPlatform);
  }

  /**
   * Update a streaming platform
   * 
   * @param streamingPlatform
   * @return StreamingPlatform
   */
  @Override
  public StreamingPlatform updateStreamingPlatform(StreamingPlatform streamingPlatform) {
    StreamingPlatform existingStreamingPlatform = streamingPlatformRepository.findById(streamingPlatform.getId())
        .orElse(null);
    if (existingStreamingPlatform == null) {
      throw new RuntimeException("Streaming platform not found");
    }
    return streamingPlatformRepository.save(streamingPlatform);
  }

  /**
   * Delete a streaming platform
   * 
   * @param id
   */
  @Override
  public void deleteStreamingPlatform(Integer id) {
    StreamingPlatform streamingPlatform = streamingPlatformRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Streaming platform not found"));
    streamingPlatformRepository.delete(streamingPlatform);
  }

  /**
   * Find all countries
   * 
   * @return List<Country>
   */
  @Override
  public List<Country> findAllCountries() {
    return countryRepository.findAll();
  }

  /**
   * Find countries pageable
   * 
   * @param name
   * @param id
   * @param pageable
   * @return Page<Country>
   */
  @Override
  public Page<Country> findCountriesPageable(String name, String id, Pageable pageable) {
    Specification<Country> spec = BaseFilterSpecifications.combine(
        BaseFilterSpecifications.hasName(name),
        BaseFilterSpecifications.hasId(id));
    return countryRepository.findAll(spec, pageable);
  }

  /**
   * Find a country by id
   * 
   * @param id
   * @return Country
   */
  @Override
  public Country findCountryById(Integer id) {
    return countryRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Country not found"));
  }

  /**
   * Create a country
   * 
   * @param country
   * @return Country
   */
  @Override
  public Country createCountry(Country country) {
    Country existingCountry = countryRepository.findByCode(country.getCode())
        .orElse(null);
    if (existingCountry != null) {
      throw new RuntimeException("Country with code " + country.getCode() + " already exists");
    }
    return countryRepository.save(country);
  }

  /**
   * Update a country
   * 
   * @param country
   * @return Country
   */
  @Override
  public Country updateCountry(Country country) {
    Country existingCountry = countryRepository.findById(country.getId())
        .orElse(null);
    if (existingCountry == null) {
      throw new RuntimeException("Country not found");
    }
    return countryRepository.save(country);
  }

  /**
   * Delete a country
   * 
   * @param id
   */
  @Override
  public void deleteCountry(Integer id) {
    Country country = countryRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Country not found"));
    countryRepository.delete(country);
  }

  /**
   * Find all show statuses
   * 
   * @return List<ShowStatus>
   */
  @Override
  public List<ShowStatus> findAllShowStatuses() {
    return statusRepository.findAll();
  }

  /**
   * Find show statuses pageable
   * 
   * @param name
   * @param id
   * @param pageable
   * @return Page<ShowStatus>
   */
  @Override
  public Page<ShowStatus> findShowStatusesPageable(String name, String id, Pageable pageable) {
    Specification<ShowStatus> spec = BaseFilterSpecifications.combine(
        BaseFilterSpecifications.hasName(name),
        BaseFilterSpecifications.hasId(id));
    return statusRepository.findAll(spec, pageable);
  }

  /**
   * Find a show status by id
   * 
   * @param id
   * @return ShowStatus
   */
  @Override
  public ShowStatus findShowStatusById(Integer id) {
    return statusRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Status not found"));
  }

  /**
   * Create a show status
   * 
   * @param status
   * @return ShowStatus
   */
  @Override
  public ShowStatus createShowStatus(ShowStatus status) {
    ShowStatus existingStatus = statusRepository.findByCode(status.getCode())
        .orElse(null);
    if (existingStatus != null) {
      throw new RuntimeException("Status with code " + status.getCode() + " already exists");
    }
    return statusRepository.save(status);
  }

  /**
   * Update a show status
   * 
   * @param status
   * @return ShowStatus
   */
  @Override
  public ShowStatus updateShowStatus(ShowStatus status) {
    ShowStatus existingStatus = statusRepository.findById(status.getId())
        .orElse(null);
    if (existingStatus == null) {
      throw new RuntimeException("Status not found");
    }
    return statusRepository.save(status);
  }

  /**
   * Delete a show status
   * 
   * @param id
   */
  @Override
  public void deleteShowStatus(Integer id) {
    ShowStatus status = statusRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Status not found"));
    statusRepository.delete(status);
  }

  /**
   * Find all languages
   * 
   * @return List<Language>
   */
  @Override
  public List<Language> findAllLanguages() {
    return languageRepository.findAll();
  }

  /**
   * Find languages pageable
   * 
   * @param name
   * @param id
   * @param pageable
   * @return Page<Language>
   */
  @Override
  public Page<Language> findLanguagesPageable(String name, String id, Pageable pageable) {
    Specification<Language> spec = BaseFilterSpecifications.combine(
        BaseFilterSpecifications.hasName(name),
        BaseFilterSpecifications.hasId(id));
    return languageRepository.findAll(spec, pageable);
  }

  /**
   * Find a language by id
   * 
   * @param id
   * @return Language
   */
  @Override
  public Language findLanguageById(Integer id) {
    return languageRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Language not found"));
  }

  /**
   * Create a language
   * 
   * @param language
   * @return Language
   */
  @Override
  public Language createLanguage(Language language) {
    Language existingLanguage = languageRepository.findByCode(language.getCode())
        .orElse(null);
    if (existingLanguage != null) {
      throw new RuntimeException("Language with code " + language.getCode() + " already exists");
    }
    return languageRepository.save(language);
  }

  /**
   * Update a language
   * 
   * @param language
   * @return Language
   */
  @Override
  public Language updateLanguage(Language language) {
    Language existingLanguage = languageRepository.findById(language.getId())
        .orElse(null);
    if (existingLanguage == null) {
      throw new RuntimeException("Language not found");
    }
    return languageRepository.save(language);
  }

  /**
   * Delete a language
   * 
   * @param id
   */
  @Override
  public void deleteLanguage(Integer id) {
    Language language = languageRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Language not found"));
    languageRepository.delete(language);
  }

  /**
   * Find all show types
   * 
   * @return List<ShowType>
   */
  @Override
  public List<ShowType> findAllShowTypes() {
    return showTypeRepository.findAll();
  }

  /**
   * Find show types pageable
   * 
   * @param name
   * @param id
   * @param pageable
   * @return Page<ShowType>
   */
  @Override
  public Page<ShowType> findShowTypesPageable(String name, String id, Pageable pageable) {
    Specification<ShowType> spec = BaseFilterSpecifications.combine(
        BaseFilterSpecifications.hasName(name),
        BaseFilterSpecifications.hasId(id));
    return showTypeRepository.findAll(spec, pageable);
  }

  /**
   * Find a show type by id
   * 
   * @param id
   * @return ShowType
   */
  @Override
  public ShowType findShowTypeById(Integer id) {
    return showTypeRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Show type not found"));
  }

  /**
   * Create a show type
   * 
   * @param showType
   * @return ShowType
   */
  @Override
  public ShowType createShowType(ShowType showType) {
    ShowType existingShowType = showTypeRepository.findByCode(showType.getCode())
        .orElse(null);
    if (existingShowType != null) {
      throw new RuntimeException("Show type with code " + showType.getCode() + " already exists");
    }
    return showTypeRepository.save(showType);
  }

  /**
   * Update a show type
   * 
   * @param showType
   * @return ShowType
   */
  @Override
  public ShowType updateShowType(ShowType showType) {
    ShowType existingShowType = showTypeRepository.findById(showType.getId())
        .orElse(null);
    if (existingShowType == null) {
      throw new RuntimeException("Show type not found");
    }
    return showTypeRepository.save(showType);
  }

  /**
   * Delete a show type
   * 
   * @param id
   */
  @Override
  public void deleteShowType(Integer id) {
    ShowType showType = showTypeRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Show type not found"));
    showTypeRepository.delete(showType);
  }
}
