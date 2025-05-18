package com.personal.api_film_rating.repository;

import com.personal.api_film_rating.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByUserIdAndRevokedIsFalseAndExpiresAtGreaterThan(UUID userId, LocalDateTime now);

    RefreshToken findByToken(String refreshToken);
}
