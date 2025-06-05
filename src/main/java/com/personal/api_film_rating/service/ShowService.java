package com.personal.api_film_rating.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.personal.api_film_rating.entity.Show;

public interface ShowService {

  Page<Show> getAllShowsPageable(Pageable pageable);

  Show getShowById(Long id);

  Show saveShow(Show show);

  void deleteShow(Long id);
}
