package com.personal.api_film_rating.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.personal.api_film_rating.entity.ShowStatus;

@Repository
public interface ShowStatusRepository extends JpaRepository<ShowStatus, Integer>, JpaSpecificationExecutor<ShowStatus> {
  Optional<ShowStatus> findByCode(String code);
}
