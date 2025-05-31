package com.personal.api_film_rating.service;

import com.personal.api_film_rating.entity.AuthenticationResponse;
import com.personal.api_film_rating.entity.LoginUser;
import com.personal.api_film_rating.entity.User;
import com.personal.api_film_rating.enums.EnumRole;
import com.personal.api_film_rating.exceptions.AuthenticationException;
import com.personal.api_film_rating.exceptions.UserAlreadyExistsException;
import com.personal.api_film_rating.exceptions.UserNotActiveException;
import com.personal.api_film_rating.repository.RoleRepository;
import com.personal.api_film_rating.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final NamingService namingService;

    public AuthenticationServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            AuthenticationManager authenticationManager, JwtService jwtService, NamingService namingService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.namingService = namingService;
    }

    /**
     * Register a new user
     * 
     * @param user
     * @return User
     */
    @Override
    public User register(User user) {
        log.info("Attempting to register user: {}", user);
        try {
            if (userRepository.findByEmail(user.getEmail()) != null) {
                throw new UserAlreadyExistsException("Email already exists");
            }

            validatePassword(user.getPassword());

            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setDisplayName(namingService.generateDisplayName(user.getEmail()));

            var role = roleRepository.findByName(EnumRole.ROLE_USER.toString()).orElse(null);
            user.setRole(role);

            User savedUser = userRepository.save(user);
            log.info("User registered successfully: {}", savedUser);

            return savedUser;
        } catch (Exception e) {
            log.error("Error registering user: {}", e.getMessage());
            throw new RuntimeException("Error registering user", e);
        }
    }

    /**
     * Verify a user
     * 
     * @param email
     * @param password
     * @return AuthenticationResponse
     */
    @Override
    public AuthenticationResponse verify(String email, String password) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));

            String accessToken = jwtService.generateAccessToken(email);
            String refreshToken = jwtService.generateRefreshToken(accessToken);
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();

            if (!loginUser.getUser().isActive()) {
                throw new UserNotActiveException("User is not active");
            }

            return new AuthenticationResponse(accessToken, refreshToken, loginUser.getUser());
        } catch (BadCredentialsException badCredentialsException) {
            throw new AuthenticationException("Invalid username or password");
        } catch (DisabledException disabledException) {
            throw new AuthenticationException("User account is disabled");
        } catch (LockedException lockedException) {
            throw new AuthenticationException("User account is locked");
        }
    }

    /**
     * Get a token
     * 
     * @param authHeader
     * @return String
     */
    @Override
    public String getToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7);
    }

    /**
     * Change a user's password
     * 
     * @param user
     * @param newPassword
     * @return User
     */
    @Override
    public User changePassword(User user, String newPassword) {
        validatePassword(newPassword);
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    /**
     * Validate a password
     * 
     * @param password
     */
    private void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c))
                hasUpperCase = true;
            else if (Character.isLowerCase(c))
                hasLowerCase = true;
            else if (Character.isDigit(c))
                hasDigit = true;
            else
                hasSpecialChar = true;
        }

        if (!hasUpperCase || !hasLowerCase || !hasDigit || !hasSpecialChar) {
            throw new IllegalArgumentException(
                    "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character");
        }
    }
}
