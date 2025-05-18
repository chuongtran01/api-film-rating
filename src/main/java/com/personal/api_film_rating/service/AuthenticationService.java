package com.personal.api_film_rating.service;

import com.personal.api_film_rating.model.AuthenticationResponse;
import com.personal.api_film_rating.model.User;

public interface AuthenticationService {
    User register(User user);

    AuthenticationResponse verify(String email, String password);

    String getToken(String token);
}
