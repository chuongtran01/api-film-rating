package com.personal.api_film_rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.personal.api_film_rating.entity.Show;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long>, JpaSpecificationExecutor<Show> {
}