package com.personal.api_auth_base.service;

import com.personal.api_auth_base.model.RefreshToken;
import com.personal.api_auth_base.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateAccessToken(String username) throws RuntimeException;

    String generateRefreshToken(String accessToken);

    RefreshToken findRefreshTokenByRefreshToken(String refreshToken);

    RefreshToken revokeRefreshToken(User user);

    void blacklistAccessToken(String accessToken);

    String extractUsername(String token);

    boolean validateToken(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);

    boolean isTokenBlacklisted(String accessToken);
}
