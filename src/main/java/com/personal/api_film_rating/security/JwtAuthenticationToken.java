package com.personal.api_film_rating.security;

import com.personal.api_film_rating.entity.JwtUserPrincipal;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public JwtAuthenticationToken(JwtUserPrincipal principal) {
        super(principal, null, List.of(new SimpleGrantedAuthority(principal.getRole())));

    }
}