package com.personal.api_film_rating.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.personal.api_film_rating.entity.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer>, JpaSpecificationExecutor<Language> {
  Optional<Language> findByCode(String code);
}
