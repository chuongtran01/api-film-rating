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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.personal.api_film_rating.dto.CreateShowDto;
import com.personal.api_film_rating.dto.ShowDto;
import com.personal.api_film_rating.entity.JwtUserPrincipal;
import com.personal.api_film_rating.entity.Show;
import com.personal.api_film_rating.entity.ShowGenre;
import com.personal.api_film_rating.entity.ShowCountry;
import com.personal.api_film_rating.entity.ShowStreamingPlatform;
import com.personal.api_film_rating.entity.StreamingPlatform;
import com.personal.api_film_rating.mapper.ShowMapper;
import com.personal.api_film_rating.service.FilterService;
import com.personal.api_film_rating.service.ShowService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/v1/shows")
public class ShowController {

  private final ShowService showService;
  private final FilterService filterService;
  private final ShowMapper showMapper;

  public ShowController(ShowService showService, ShowMapper showMapper, FilterService filterService) {
    this.showService = showService;
    this.showMapper = showMapper;
    this.filterService = filterService;
  }
}
