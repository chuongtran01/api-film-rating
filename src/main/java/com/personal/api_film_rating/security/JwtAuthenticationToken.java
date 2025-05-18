package com.personal.api_film_rating.security;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String id;
    private final String role;

    public JwtAuthenticationToken(String id, String email, String role) {
        super(email, null, List.of(new SimpleGrantedAuthority(role)));

        this.id = id;
        this.role = role;
    }
}