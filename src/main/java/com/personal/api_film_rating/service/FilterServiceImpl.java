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
import com.personal.api_film_rating.entity.StreamingPlatform;
import com.personal.api_film_rating.repository.CountryRepository;
import com.personal.api_film_rating.repository.GenreRepository;
import com.personal.api_film_rating.repository.LanguageRepository;
import com.personal.api_film_rating.repository.ShowStatusRepository;
import com.personal.api_film_rating.repository.StreamingPlatformRepository;
import com.personal.api_film_rating.specifications.BaseFilterSpecifications;
import com.personal.api_film_rating.specifications.BaseFilterSpecifications;

@Service
public class FilterServiceImpl implements FilterService {
  private GenreRepository genreRepository;
  private StreamingPlatformRepository streamingPlatformRepository;
  private CountryRepository countryRepository;
  private ShowStatusRepository statusRepository;
  private LanguageRepository languageRepository;

  FilterServiceImpl(GenreRepository genreRepository, StreamingPlatformRepository streamingPlatformRepository,
      CountryRepository countryRepository, ShowStatusRepository statusRepository,
      LanguageRepository languageRepository) {
    this.genreRepository = genreRepository;
    this.streamingPlatformRepository = streamingPlatformRepository;
    this.countryRepository = countryRepository;
    this.statusRepository = statusRepository;
    this.languageRepository = languageRepository;
  }

  @Override
  public Page<Genre> findGenresPageable(String name, String id, Pageable pageable) {
    Specification<Genre> spec = BaseFilterSpecifications.combine(
        BaseFilterSpecifications.hasName(name),
        BaseFilterSpecifications.hasId(id));
    return genreRepository.findAll(spec, pageable);
  }

  @Override
  public List<Genre> findAllGenres() {
    return genreRepository.findAll();
  }

  @Override
  public Genre findGenreById(String id) {
    return genreRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Genre not found"));
  }

  @Override
  public Genre createGenre(Genre genre) {
    Genre existingGenre = genreRepository.findById(genre.getId())
        .orElse(null);
    if (existingGenre != null) {
      throw new RuntimeException("Genre already exists");
    }
    return genreRepository.save(genre);
  }

  @Override
  public Genre updateGenre(Genre genre) {
    Genre existingGenre = genreRepository.findById(genre.getId())
        .orElse(null);
    if (existingGenre == null) {
      throw new RuntimeException("Genre not found");
    }
    return genreRepository.save(genre);
  }

  @Override
  public void deleteGenre(String id) {
    Genre genre = genreRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Genre not found"));
    genreRepository.delete(genre);
  }

  @Override
  public List<StreamingPlatform> findAllStreamingPlatforms() {
    return streamingPlatformRepository.findAll();
  }

  @Override
  public Page<StreamingPlatform> findStreamingPlatformsPageable(String name, String id, Pageable pageable) {
    Specification<StreamingPlatform> spec = BaseFilterSpecifications.combine(
        BaseFilterSpecifications.hasName(name),
        BaseFilterSpecifications.hasId(id));
    return streamingPlatformRepository.findAll(spec, pageable);
  }

  @Override
  public StreamingPlatform findStreamingPlatformById(String id) {
    return streamingPlatformRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Streaming platform not found"));
  }

  @Override
  public StreamingPlatform createStreamingPlatform(StreamingPlatform streamingPlatform) {
    StreamingPlatform existingStreamingPlatform = streamingPlatformRepository.findById(streamingPlatform.getId())
        .orElse(null);
    if (existingStreamingPlatform != null) {
      throw new RuntimeException("Streaming platform already exists");
    }
    return streamingPlatformRepository.save(streamingPlatform);
  }

  @Override
  public StreamingPlatform updateStreamingPlatform(StreamingPlatform streamingPlatform) {
    StreamingPlatform existingStreamingPlatform = streamingPlatformRepository.findById(streamingPlatform.getId())
        .orElse(null);
    if (existingStreamingPlatform == null) {
      throw new RuntimeException("Streaming platform not found");
    }
    return streamingPlatformRepository.save(streamingPlatform);
  }

  @Override
  public void deleteStreamingPlatform(String id) {
    StreamingPlatform streamingPlatform = streamingPlatformRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Streaming platform not found"));
    streamingPlatformRepository.delete(streamingPlatform);
  }

  @Override
  public List<Country> findAllCountries() {
    return countryRepository.findAll();
  }

  @Override
  public Page<Country> findCountriesPageable(String name, String id, Pageable pageable) {
    Specification<Country> spec = BaseFilterSpecifications.combine(
        BaseFilterSpecifications.hasName(name),
        BaseFilterSpecifications.hasId(id));
    return countryRepository.findAll(spec, pageable);
  }

  @Override
  public Country findCountryById(String id) {
    return countryRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Country not found"));
  }

  @Override
  public Country createCountry(Country country) {
    Country existingCountry = countryRepository.findById(country.getId())
        .orElse(null);
    if (existingCountry != null) {
      throw new RuntimeException("Country already exists");
    }
    return countryRepository.save(country);
  }

  @Override
  public Country updateCountry(Country country) {
    Country existingCountry = countryRepository.findById(country.getId())
        .orElse(null);
    if (existingCountry == null) {
      throw new RuntimeException("Country not found");
    }
    return countryRepository.save(country);
  }

  @Override
  public void deleteCountry(String id) {
    Country country = countryRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Country not found"));
    countryRepository.delete(country);
  }

  @Override
  public List<ShowStatus> findAllShowStatuses() {
    return statusRepository.findAll();
  }

  @Override
  public Page<ShowStatus> findShowStatusesPageable(String name, String id, Pageable pageable) {
    Specification<ShowStatus> spec = BaseFilterSpecifications.combine(
        BaseFilterSpecifications.hasName(name),
        BaseFilterSpecifications.hasId(id));
    return statusRepository.findAll(spec, pageable);
  }

  @Override
  public ShowStatus findShowStatusById(String id) {
    return statusRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Status not found"));
  }

  @Override
  public ShowStatus createShowStatus(ShowStatus status) {
    ShowStatus existingStatus = statusRepository.findById(status.getId())
        .orElse(null);
    if (existingStatus != null) {
      throw new RuntimeException("Status already exists");
    }
    return statusRepository.save(status);
  }

  @Override
  public ShowStatus updateShowStatus(ShowStatus status) {
    ShowStatus existingStatus = statusRepository.findById(status.getId())
        .orElse(null);
    if (existingStatus == null) {
      throw new RuntimeException("Status not found");
    }
    return statusRepository.save(status);
  }

  @Override
  public void deleteShowStatus(String id) {
    ShowStatus status = statusRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Status not found"));
    statusRepository.delete(status);
  }

  @Override
  public List<Language> findAllLanguages() {
    return languageRepository.findAll();
  }

  @Override
  public Page<Language> findLanguagesPageable(String name, String id, Pageable pageable) {
    Specification<Language> spec = BaseFilterSpecifications.combine(
        BaseFilterSpecifications.hasName(name),
        BaseFilterSpecifications.hasId(id));
    return languageRepository.findAll(spec, pageable);
  }

  @Override
  public Language findLanguageById(String id) {
    return languageRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Language not found"));
  }

  @Override
  public Language createLanguage(Language language) {
    Language existingLanguage = languageRepository.findById(language.getId())
        .orElse(null);
    if (existingLanguage != null) {
      throw new RuntimeException("Language already exists");
    }
    return languageRepository.save(language);
  }

  @Override
  public Language updateLanguage(Language language) {
    Language existingLanguage = languageRepository.findById(language.getId())
        .orElse(null);
    if (existingLanguage == null) {
      throw new RuntimeException("Language not found");
    }
    return languageRepository.save(language);
  }

  @Override
  public void deleteLanguage(String id) {
    Language language = languageRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Language not found"));
    languageRepository.delete(language);
  }
}
