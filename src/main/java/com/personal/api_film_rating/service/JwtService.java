package com.personal.api_film_rating.service;

import com.personal.api_film_rating.entity.RefreshToken;
import com.personal.api_film_rating.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateAccessToken(String username) throws RuntimeException;

    String generateRefreshToken(String accessToken);

    RefreshToken findRefreshTokenByRefreshToken(String refreshToken);

    RefreshToken revokeRefreshToken(User user);

    void blacklistAccessToken(String accessToken);

    String extractEmail(String token);

    Claims extractAllClaims(String token);

    boolean validateToken(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);

    boolean isTokenBlacklisted(String accessToken);
}
