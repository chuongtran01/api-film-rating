package com.personal.api_film_rating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableJpaRepositories
@EnableMethodSecurity
@EnableJpaAuditing
public class ApiFilmRatingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiFilmRatingApplication.class, args);
    }

}
