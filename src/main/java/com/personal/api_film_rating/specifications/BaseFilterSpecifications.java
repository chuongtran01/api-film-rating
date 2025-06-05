package com.personal.api_film_rating.specifications;

import org.springframework.data.jpa.domain.Specification;

public class BaseFilterSpecifications {
  /**
   * Has code
   * 
   * @param <T>  Entity type
   * @param code Code to filter by
   * @return Specification<T>
   */
  public static <T> Specification<T> hasId(String code) {
    return (root, query, cb) -> {
      if (code == null) {
        return cb.conjunction(); // no filter
      }
      return cb.like(root.get("code"), "%" + code + "%");
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