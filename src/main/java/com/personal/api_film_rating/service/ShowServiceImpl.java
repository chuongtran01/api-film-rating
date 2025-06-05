package com.personal.api_film_rating.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.personal.api_film_rating.entity.Show;
import com.personal.api_film_rating.repository.ShowRepository;

@Service
public class ShowServiceImpl implements ShowService {

  private final ShowRepository showRepository;

  public ShowServiceImpl(ShowRepository showRepository) {
    this.showRepository = showRepository;
  }

  /**
   * Get all shows pageable
   * 
   * @param pageable
   * @return Page<Show>
   */
  @Override
  public Page<Show> getAllShowsPageable(Pageable pageable) {
    return showRepository.findAll(pageable);
  }

  /**
   * Get a show by id
   * 
   * @param id
   * @return Show
   */
  @Override
  public Show getShowById(Long id) {
    return showRepository.findById(id).orElse(null);
  }

  /**
   * Save a show
   * 
   * @param show
   * @return Show
   */
  @Override
  public Show saveShow(Show show) {
    return showRepository.save(show);
  }

  /**
   * Delete a show
   * 
   * @param id
   */
  @Override
  public void deleteShow(Long id) {
    Show show = showRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Show not found"));
    showRepository.delete(show);
  }
}
