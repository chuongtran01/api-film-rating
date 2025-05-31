package com.personal.api_film_rating.service;

import com.personal.api_film_rating.entity.Role;
import com.personal.api_film_rating.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User findByUserId(UUID id);

    User saveUser(User user);

    Page<User> findUsers(List<String> role, List<String> gender, String displayName, String email, Boolean active,
            Pageable pageable);

    void inactiveUserById(UUID id);

    Role findRoleByName(String name);
}
