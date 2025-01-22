package com.personal.api_auth_base.repository;

import com.personal.api_auth_base.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByUserUserIdAndRevokedIsFalseAndExpiresAtGreaterThan(Long userId, LocalDateTime now);

    RefreshToken findByToken(String refreshToken);
}
