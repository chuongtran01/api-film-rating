package com.personal.api_film_rating.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.personal.api_film_rating.entity.Show;

public class ShowSpecifications {
  public static Specification<Show> hasTitle(String title) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + title + "%");
  }
}
