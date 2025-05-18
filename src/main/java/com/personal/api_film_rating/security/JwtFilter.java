package com.personal.api_film_rating.security;

import com.personal.api_film_rating.dto.JwtErrorResponseDto;
import com.personal.api_film_rating.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authHeader.substring(7);

            if (jwtService.isTokenBlacklisted(token)) {
                throw new JwtException("Token is blacklisted");
            }

            Claims claims = jwtService.extractAllClaims(token);
            String email = claims.getSubject();

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                JwtAuthenticationToken authToken = new JwtAuthenticationToken(
                        claims.get("id", String.class),
                        email,
                        claims.get("role", String.class));

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (ExpiredJwtException expiredJwtException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            JwtErrorResponseDto jwtErrorResponseDto = new JwtErrorResponseDto("expired_token",
                    "The token provided is expired.");
            response.getWriter().write(jwtErrorResponseDto.writeValueAsString());
            return;
        } catch (JwtException jwtException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            JwtErrorResponseDto jwtErrorResponseDto = new JwtErrorResponseDto("invalid_token",
                    "The token provided is invalid.");
            response.getWriter().write(jwtErrorResponseDto.writeValueAsString());
            return;
        } catch (Exception e) {
            return;
        }

        filterChain.doFilter(request, response);
    }
}
