package com.personal.api_film_rating.service;

import com.personal.api_film_rating.entity.LoginUser;
import com.personal.api_film_rating.entity.User;
import com.personal.api_film_rating.exceptions.UserNotActiveException;
import com.personal.api_film_rating.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        if (!user.isActive()) {
            throw new UserNotActiveException("User is not active");
        }

        return new LoginUser(user);
    }
}
