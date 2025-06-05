package com.personal.api_film_rating.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.personal.api_film_rating.dto.CountryDto;
import com.personal.api_film_rating.dto.FilterDto;
import com.personal.api_film_rating.dto.GenreDto;
import com.personal.api_film_rating.dto.LanguageDto;
import com.personal.api_film_rating.dto.ShowStatusDto;
import com.personal.api_film_rating.dto.ShowTypeDto;
import com.personal.api_film_rating.dto.StreamingPlatformDto;
import com.personal.api_film_rating.entity.Country;
import com.personal.api_film_rating.entity.Genre;
import com.personal.api_film_rating.entity.StreamingPlatform;
import com.personal.api_film_rating.entity.JwtUserPrincipal;
import com.personal.api_film_rating.entity.Language;
import com.personal.api_film_rating.entity.ShowStatus;
import com.personal.api_film_rating.entity.ShowType;
import com.personal.api_film_rating.mapper.GenreMapper;
import com.personal.api_film_rating.mapper.CountryMapper;
import com.personal.api_film_rating.mapper.LanguageMapper;
import com.personal.api_film_rating.mapper.StreamingPlatformMapper;
import com.personal.api_film_rating.mapper.ShowStatusMapper;
import com.personal.api_film_rating.mapper.ShowTypeMapper;
import com.personal.api_film_rating.service.FilterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/filters")
public class FilterController {

  private FilterService filterService;
  private GenreMapper genreMapper;
  private StreamingPlatformMapper streamingPlatformMapper;
  private CountryMapper countryMapper;
  private LanguageMapper languageMapper;
  private ShowStatusMapper statusMapper;
  private ShowTypeMapper showTypeMapper;

  FilterController(FilterService filterService, GenreMapper genreMapper,
      StreamingPlatformMapper streamingPlatformMapper, CountryMapper countryMapper, LanguageMapper languageMapper,
      ShowStatusMapper statusMapper, ShowTypeMapper showTypeMapper) {
    this.filterService = filterService;
    this.genreMapper = genreMapper;
    this.streamingPlatformMapper = streamingPlatformMapper;
    this.countryMapper = countryMapper;
    this.languageMapper = languageMapper;
    this.statusMapper = statusMapper;
    this.showTypeMapper = showTypeMapper;
  }

  /**
   * Get all filters
   * 
   * @param loginUser
   * @return FilterDto
   */
  @GetMapping
  public ResponseEntity<FilterDto> getFilters(@AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to get filters");
    }

    List<GenreDto> genres = filterService.findAllGenres().stream()
        .map(genreMapper::toGenreDto)
        .collect(Collectors.toList());
    List<StreamingPlatformDto> streamingPlatforms = filterService.findAllStreamingPlatforms().stream()
        .map(streamingPlatformMapper::toStreamingPlatformDto)
        .collect(Collectors.toList());
    List<CountryDto> countries = filterService.findAllCountries().stream()
        .map(countryMapper::toCountryDto)
        .collect(Collectors.toList());
    List<ShowStatusDto> showStatuses = filterService.findAllShowStatuses().stream()
        .map(statusMapper::toStatusDto)
        .collect(Collectors.toList());
    List<LanguageDto> languages = filterService.findAllLanguages().stream()
        .map(languageMapper::toLanguageDto)
        .collect(Collectors.toList());
    List<ShowTypeDto> showTypes = filterService.findAllShowTypes().stream()
        .map(showTypeMapper::toShowTypeDto)
        .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK)
        .body(new FilterDto(genres, streamingPlatforms, countries, showStatuses, languages, showTypes));
  }

  /**
   * Get all genres with pagination and filtering
   * 
   * @param loginUser
   * @param name
   * @param id
   * @param pageable
   * @return ResponseEntity<PagedModel<GenreDto>>
   */
  @GetMapping("/genres")
  public ResponseEntity<PagedModel<GenreDto>> getGenres(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String code,
      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to get genres");
    }
    return ResponseEntity.status(HttpStatus.OK)
        .body(new PagedModel<>(
            filterService.findGenresPageable(name, code, pageable).map(genreMapper::toGenreDto)));
  }

  /**
   * Get all genres
   * 
   * @return List<GenreDto>
   */
  @GetMapping("/genres/all")
  public ResponseEntity<List<GenreDto>> getAllGenres() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(filterService.findAllGenres().stream()
            .map(genreMapper::toGenreDto)
            .collect(Collectors.toList()));
  }

  /**
   * Create a new genre
   * 
   * @param genreDto
   * @param loginUser
   * @return GenreDto
   */
  @PostMapping("/genres")
  public ResponseEntity<GenreDto> createGenre(@RequestBody @Valid GenreDto genreDto,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to create a genre");
    }

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(genreMapper.toGenreDto(filterService.createGenre(genreMapper.toGenre(genreDto))));
  }

  /**
   * Delete a genre
   * 
   * @param id
   * @param loginUser
   * @return Void
   */
  @DeleteMapping("/genres/{id}")
  public ResponseEntity<Void> deleteGenre(@PathVariable Integer id,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to delete a genre");
    }
    filterService.deleteGenre(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /**
   * Update a genre
   * 
   * @param id
   * @param genreDto
   * @param loginUser
   * @return GenreDto
   */
  @PutMapping("/genres/{id}")
  public ResponseEntity<GenreDto> updateGenre(@PathVariable Integer id, @RequestBody GenreDto genreDto,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update a genre");
    }
    Genre genre = filterService.findGenreById(id);
    genre.setCode(genreDto.code());
    genre.setName(genreDto.name());

    return ResponseEntity.status(HttpStatus.OK)
        .body(genreMapper.toGenreDto(filterService.updateGenre(genre)));
  }

  /**
   * Get all streaming platforms with pagination and filtering
   * 
   * @param loginUser
   * @param name
   * @param id
   * @param pageable
   * @return ResponseEntity<PagedModel<StreamingPlatformDto>>
   */
  @GetMapping("/streaming-platforms")
  public ResponseEntity<PagedModel<StreamingPlatformDto>> getStreamingPlatforms(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String code,
      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to get streaming platforms");
    }
    return ResponseEntity.status(HttpStatus.OK)
        .body(new PagedModel<>(
            filterService.findStreamingPlatformsPageable(name, code, pageable)
                .map(streamingPlatformMapper::toStreamingPlatformDto)));
  }

  /**
   * Get all streaming platforms
   * 
   * @return List<StreamingPlatformDto>
   */
  @GetMapping("/streaming-platforms/all")
  public ResponseEntity<List<StreamingPlatformDto>> getAllStreamingPlatforms() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(filterService.findAllStreamingPlatforms().stream()
            .map(streamingPlatformMapper::toStreamingPlatformDto)
            .collect(Collectors.toList()));
  }

  /**
   * Create a new streaming platform
   * 
   * @param streamingPlatformDto
   * @param loginUser
   * @return StreamingPlatformDto
   */
  @PostMapping("/streaming-platforms")
  public ResponseEntity<StreamingPlatformDto> createStreamingPlatform(
      @RequestBody StreamingPlatformDto streamingPlatformDto,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to create a streaming platform");
    }

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(streamingPlatformMapper.toStreamingPlatformDto(
            filterService.createStreamingPlatform(streamingPlatformMapper.toStreamingPlatform(streamingPlatformDto))));
  }

  /**
   * Update a streaming platform
   * 
   * @param id
   * @param streamingPlatformDto
   * @param loginUser
   * @return StreamingPlatformDto
   */
  @PutMapping("/streaming-platforms/{id}")
  public ResponseEntity<StreamingPlatformDto> updateStreamingPlatform(@PathVariable Integer id,
      @RequestBody StreamingPlatformDto streamingPlatformDto,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update a streaming platform");
    }
    StreamingPlatform streamingPlatform = filterService.findStreamingPlatformById(id);
    streamingPlatform.setCode(streamingPlatformDto.code());
    streamingPlatform.setName(streamingPlatformDto.name());
    streamingPlatform.setUrl(streamingPlatformDto.url());
    streamingPlatform.setLogo(streamingPlatformDto.logo());

    return ResponseEntity.status(HttpStatus.OK)
        .body(streamingPlatformMapper.toStreamingPlatformDto(filterService.updateStreamingPlatform(streamingPlatform)));
  }

  /**
   * Delete a streaming platform
   * 
   * @param id
   * @param loginUser
   * @return Void
   */
  @DeleteMapping("/streaming-platforms/{id}")
  public ResponseEntity<Void> deleteStreamingPlatform(@PathVariable Integer id,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to delete a streaming platform");
    }
    filterService.deleteStreamingPlatform(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /**
   * Get all countries with pagination and filtering
   * 
   * @param loginUser
   * @param name
   * @param id
   * @param pageable
   * @return ResponseEntity<PagedModel<CountryDto>>
   */
  @GetMapping("/countries")
  public ResponseEntity<PagedModel<CountryDto>> getCountries(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String code,
      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to get countries");
    }
    return ResponseEntity.status(HttpStatus.OK)
        .body(new PagedModel<>(
            filterService.findCountriesPageable(name, code, pageable).map(countryMapper::toCountryDto)));
  }

  /**
   * Get all countries
   * 
   * @return List<CountryDto>
   */
  @GetMapping("/countries/all")
  public ResponseEntity<List<CountryDto>> getAllCountries() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(filterService.findAllCountries().stream()
            .map(countryMapper::toCountryDto)
            .collect(Collectors.toList()));
  }

  /**
   * Create a new country
   * 
   * @param countryDto
   * @param loginUser
   * @return CountryDto
   */
  @PostMapping("/countries")
  public ResponseEntity<CountryDto> createCountry(@RequestBody CountryDto countryDto,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to create a country");
    }

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(countryMapper.toCountryDto(filterService.createCountry(countryMapper.toCountry(countryDto))));
  }

  /**
   * Update a country
   * 
   * @param id
   * @param countryDto
   * @param loginUser
   * @return CountryDto
   */
  @PutMapping("/countries/{id}")
  public ResponseEntity<CountryDto> updateCountry(@PathVariable Integer id, @RequestBody CountryDto countryDto,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update a country");
    }
    Country country = filterService.findCountryById(id);
    country.setCode(countryDto.code());
    country.setName(countryDto.name());
    country.setFlag(countryDto.flag());

    return ResponseEntity.status(HttpStatus.OK)
        .body(countryMapper.toCountryDto(filterService.updateCountry(country)));
  }

  /**
   * Delete a country
   * 
   * @param id
   * @param loginUser
   * @return Void
   */
  @DeleteMapping("/countries/{id}")
  public ResponseEntity<Void> deleteCountry(@PathVariable Integer id,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to delete a country");
    }
    filterService.deleteCountry(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /**
   * Get all languages with pagination and filtering
   * 
   * @param loginUser
   * @param name
   * @param id
   * @param pageable
   * @return ResponseEntity<PagedModel<LanguageDto>>
   */
  @GetMapping("/languages")
  public ResponseEntity<PagedModel<LanguageDto>> getLanguages(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String code,
      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to get languages");
    }
    return ResponseEntity.status(HttpStatus.OK)
        .body(new PagedModel<>(
            filterService.findLanguagesPageable(name, code, pageable).map(languageMapper::toLanguageDto)));
  }

  /**
   * Get all languages
   * 
   * @return List<LanguageDto>
   */
  @GetMapping("/languages/all")
  public ResponseEntity<List<LanguageDto>> getAllLanguages() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(filterService.findAllLanguages().stream()
            .map(languageMapper::toLanguageDto)
            .collect(Collectors.toList()));
  }

  /**
   * Create a new language
   * 
   * @param languageDto
   * @param loginUser
   * @return LanguageDto
   */
  @PostMapping("/languages")
  public ResponseEntity<LanguageDto> createLanguage(@RequestBody LanguageDto languageDto,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to create a language");
    }

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(languageMapper.toLanguageDto(filterService.createLanguage(languageMapper.toLanguage(languageDto))));
  }

  /**
   * Update a language
   * 
   * @param id
   * @param languageDto
   * @param loginUser
   * @return LanguageDto
   */
  @PutMapping("/languages/{id}")
  public ResponseEntity<LanguageDto> updateLanguage(@PathVariable Integer id, @RequestBody LanguageDto languageDto,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update a language");
    }
    Language language = filterService.findLanguageById(id);
    language.setCode(languageDto.code());
    language.setName(languageDto.name());

    return ResponseEntity.status(HttpStatus.OK)
        .body(languageMapper.toLanguageDto(filterService.updateLanguage(language)));
  }

  /**
   * Delete a language
   * 
   * @param id
   * @param loginUser
   * @return Void
   */
  @DeleteMapping("/languages/{id}")
  public ResponseEntity<Void> deleteLanguage(@PathVariable Integer id,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to delete a language");
    }
    filterService.deleteLanguage(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /**
   * Get all show statuses with pagination and filtering
   * 
   * @param loginUser
   * @param name
   * @param id
   * @param pageable
   * @return ResponseEntity<PagedModel<ShowStatusDto>>
   */
  @GetMapping("/show-statuses")
  public ResponseEntity<PagedModel<ShowStatusDto>> getShowStatuses(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String code,
      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to get show statuses");
    }
    return ResponseEntity.status(HttpStatus.OK)
        .body(new PagedModel<>(
            filterService.findShowStatusesPageable(name, code, pageable).map(statusMapper::toStatusDto)));
  }

  /**
   * Get all show statuses
   * 
   * @return List<ShowStatusDto>
   */
  @GetMapping("/show-statuses/all")
  public ResponseEntity<List<ShowStatusDto>> getAllShowStatuses() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(filterService.findAllShowStatuses().stream()
            .map(statusMapper::toStatusDto)
            .collect(Collectors.toList()));
  }

  /**
   * Create a new show status
   * 
   * @param statusDto
   * @param loginUser
   * @return ShowStatusDto
   */
  @PostMapping("/show-statuses")
  public ResponseEntity<ShowStatusDto> createShowStatus(@RequestBody ShowStatusDto statusDto,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to create a show status");
    }

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(statusMapper.toStatusDto(filterService.createShowStatus(statusMapper.toStatus(statusDto))));
  }

  /**
   * Update a show status
   * 
   * @param id
   * @param statusDto
   * @param loginUser
   * @return ShowStatusDto
   */
  @PutMapping("/show-statuses/{id}")
  public ResponseEntity<ShowStatusDto> updateShowStatus(@PathVariable Integer id, @RequestBody ShowStatusDto statusDto,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update a show status");
    }
    ShowStatus status = filterService.findShowStatusById(id);
    status.setCode(statusDto.code());
    status.setName(statusDto.name());

    return ResponseEntity.status(HttpStatus.OK)
        .body(statusMapper.toStatusDto(filterService.updateShowStatus(status)));
  }

  /**
   * Delete a show status
   * 
   * @param id
   * @param loginUser
   * @return Void
   */
  @DeleteMapping("/show-statuses/{id}")
  public ResponseEntity<Void> deleteShowStatus(@PathVariable Integer id,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to delete a show status");
    }
    filterService.deleteShowStatus(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /**
   * Get all show types with pagination and filtering
   * 
   * @param loginUser
   * @param name
   * @param id
   * @param pageable
   * @return ResponseEntity<PagedModel<ShowTypeDto>>
   */
  @GetMapping("/show-types")
  public ResponseEntity<PagedModel<ShowTypeDto>> getShowTypes(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String code,
      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to get show types");
    }
    return ResponseEntity.status(HttpStatus.OK)
        .body(new PagedModel<>(
            filterService.findShowTypesPageable(name, code, pageable).map(showTypeMapper::toShowTypeDto)));
  }

  /**
   * Get all show types
   * 
   * @return List<ShowTypeDto>
   */
  @GetMapping("/show-types/all")
  public ResponseEntity<List<ShowTypeDto>> getAllShowTypes() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(filterService.findAllShowTypes().stream()
            .map(showTypeMapper::toShowTypeDto)
            .collect(Collectors.toList()));
  }

  /**
   * Create a new show type
   * 
   * @param showTypeDto
   * @param loginUser
   * @return ShowTypeDto
   */
  @PostMapping("/show-types")
  public ResponseEntity<ShowTypeDto> createShowType(@RequestBody ShowTypeDto showTypeDto,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to create a show type");
    }
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(showTypeMapper.toShowTypeDto(filterService.createShowType(showTypeMapper.toShowType(showTypeDto))));
  }

  /**
   * Update a show type
   * 
   * @param id
   * @param showTypeDto
   * @param loginUser
   * @return ShowTypeDto
   */
  @PutMapping("/show-types/{id}")
  public ResponseEntity<ShowTypeDto> updateShowType(@PathVariable Integer id, @RequestBody ShowTypeDto showTypeDto,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update a show type");
    }
    ShowType showType = filterService.findShowTypeById(id);
    showType.setCode(showTypeDto.code());
    showType.setName(showTypeDto.name());

    return ResponseEntity.status(HttpStatus.OK)
        .body(showTypeMapper.toShowTypeDto(filterService.updateShowType(showType)));
  }

  /**
   * Delete a show type
   * 
   * @param id
   * @param loginUser
   * @return Void
   */
  @DeleteMapping("/show-types/{id}")
  public ResponseEntity<Void> deleteShowType(@PathVariable Integer id,
      @AuthenticationPrincipal JwtUserPrincipal loginUser) {
    if (!loginUser.isAdmin() && !loginUser.isModerator()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to delete a show type");
    }
    filterService.deleteShowType(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
