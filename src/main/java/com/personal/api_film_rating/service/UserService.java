package com.personal.api_film_rating.service;

import com.personal.api_film_rating.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    User findByUserId(UUID id);

    User save(User user);

    Page<User> findUsersByRole(String role, Pageable pageable);
}
