package com.personal.api_film_rating.controller;

import com.personal.api_film_rating.dto.*;
import com.personal.api_film_rating.mapper.UserMapper;
import com.personal.api_film_rating.model.AuthenticationResponse;
import com.personal.api_film_rating.model.LoginUser;
import com.personal.api_film_rating.model.RefreshToken;
import com.personal.api_film_rating.model.User;
import com.personal.api_film_rating.service.AuthenticationService;
import com.personal.api_film_rating.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final ApplicationContext applicationContext;
    private final UserMapper userMapper;

    public AuthController(AuthenticationService authenticationService, JwtService jwtService,
                          ApplicationContext applicationContext, UserMapper userMapper) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
        this.applicationContext = applicationContext;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody @Valid SignUpDto signUpDto) {
        User newUser = authenticationService.register(userMapper.toUser(signUpDto));
        return userMapper.toUserDto(newUser);
    }

    @PostMapping("/login")
    public AuthenticationResponseDto login(@RequestBody @Valid SignInDto signInDto) throws Exception {
        try {
            AuthenticationResponse authenticationResponse = authenticationService.verify(signInDto.getEmail(), signInDto.getPassword());

            String accessToken = authenticationResponse.getAccessToken();
            String refreshToken = authenticationResponse.getRefreshToken();

            return new AuthenticationResponseDto(accessToken, refreshToken);
        } catch (AuthenticationException authenticationException) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, authenticationException.getMessage());
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/refresh-token")
    public AuthenticationResponseDto refreshToken(@RequestBody @Valid RefreshAccessTokenDto refreshAccessTokenDto) {
        RefreshToken token = jwtService.findRefreshTokenByRefreshToken(refreshAccessTokenDto.getRefreshToken());
        String accessToken = jwtService.generateAccessToken(token.getUser().getDisplayName());
        return new AuthenticationResponseDto(accessToken, null);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        String accessToken = authHeader.substring(7);
        jwtService.blacklistAccessToken(accessToken); // Blacklist access token in redis

        LoginUser loginUser = (LoginUser) applicationContext.getBean(UserDetailsService.class)
                .loadUserByUsername(jwtService.extractEmail(accessToken));
        jwtService.revokeRefreshToken(loginUser.getUser()); // Invalidate refresh tokens on logout
    }
}