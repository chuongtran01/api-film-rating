package com.personal.api_film_rating.service;

import com.personal.api_film_rating.entity.AuthenticationResponse;
import com.personal.api_film_rating.entity.User;

public interface AuthenticationService {
    User register(User user);

    AuthenticationResponse verify(String email, String password);

    String getToken(String token);
}
