package com.personal.api_film_rating.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.personal.api_film_rating.model.RefreshToken;
import com.personal.api_film_rating.model.User;

import io.jsonwebtoken.Claims;

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
