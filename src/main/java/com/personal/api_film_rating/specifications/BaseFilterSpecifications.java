package com.personal.api_film_rating.specifications;

import org.springframework.data.jpa.domain.Specification;

public class BaseFilterSpecifications {
  /**
   * Has id
   * 
   * @param <T> Entity type
   * @param id  ID to filter by
   * @return Specification<T>
   */
  public static <T> Specification<T> hasId(String id) {
    return (root, query, cb) -> {
      if (id == null) {
        return cb.conjunction(); // no filter
      }
      return cb.like(root.get("id"), "%" + id + "%");
    };
  }

  /**
   * Has name
   * 
   * @param <T>  Entity type
   * @param name Name to filter by
   * @return Specification<T>
   */
  public static <T> Specification<T> hasName(String name) {
    return (root, query, cb) -> {
      if (name == null) {
        return cb.conjunction(); // no filter
      }
      return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    };
  }

  /**
   * Combine specifications
   * 
   * @param <T>   Entity type
   * @param specs Specifications to combine
   * @return Combined specification
   */
  @SafeVarargs
  public static <T> Specification<T> combine(Specification<T>... specs) {
    Specification<T> result = null;
    for (Specification<T> spec : specs) {
      if (result == null) {
        result = spec;
      } else {
        result = result.and(spec);
      }
    }
    return result;
  }
}