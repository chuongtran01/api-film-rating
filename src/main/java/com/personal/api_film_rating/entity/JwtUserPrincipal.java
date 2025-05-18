package com.personal.api_film_rating.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtUserPrincipal {
    private String id;
    private String email;
    private String role;
}
