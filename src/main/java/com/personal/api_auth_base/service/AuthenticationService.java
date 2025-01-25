package com.personal.api_auth_base.service;

import com.personal.api_auth_base.model.AuthenticationResponse;
import com.personal.api_auth_base.model.User;

public interface AuthenticationService {
    User register(User user);

    AuthenticationResponse verify(User user);

    String getToken(String token);
}
