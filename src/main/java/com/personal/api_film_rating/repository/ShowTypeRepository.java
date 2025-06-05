package com.personal.api_film_rating.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.personal.api_film_rating.entity.ShowType;

@Repository
public interface ShowTypeRepository extends JpaRepository<ShowType, Integer>, JpaSpecificationExecutor<ShowType> {
  Optional<ShowType> findByCode(String code);
}
