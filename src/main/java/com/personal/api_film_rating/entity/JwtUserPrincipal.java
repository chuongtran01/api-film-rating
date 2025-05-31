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

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_MODERATOR = "ROLE_MODERATOR";
    public static final String ROLE_USER = "ROLE_USER";

    private String id;
    private String email;
    private String role;

    public boolean isAdmin() {
        return ROLE_ADMIN.equals(role);
    }

    public boolean isModerator() {
        return ROLE_MODERATOR.equals(role);
    }
}
