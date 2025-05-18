package com.personal.api_film_rating.service;

import com.personal.api_film_rating.entity.User;

import java.util.UUID;

public interface UserService {
    User findByUserId(UUID id);

    User save(User user);
}
