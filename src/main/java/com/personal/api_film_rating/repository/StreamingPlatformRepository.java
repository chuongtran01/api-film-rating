package com.personal.api_film_rating.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.personal.api_film_rating.entity.StreamingPlatform;

@Repository
public interface StreamingPlatformRepository
        extends JpaRepository<StreamingPlatform, Integer>, JpaSpecificationExecutor<StreamingPlatform> {
    Optional<StreamingPlatform> findByCode(String code);
}
