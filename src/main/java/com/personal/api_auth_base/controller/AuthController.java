package com.personal.api_auth_base.controller;

import com.personal.api_auth_base.dto.*;
import com.personal.api_auth_base.model.AuthenticationResponse;
import com.personal.api_auth_base.model.LoginUser;
import com.personal.api_auth_base.model.RefreshToken;
import com.personal.api_auth_base.model.User;
import com.personal.api_auth_base.service.AuthenticationService;
import com.personal.api_auth_base.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    private final JwtService jwtService;

    private final ApplicationContext applicationContext;

    public AuthController(AuthenticationService authenticationService, JwtService jwtService, ApplicationContext applicationContext) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
        this.applicationContext = applicationContext;
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody @Valid CreateUserDto createUserDto) {
        User newUser = authenticationService.register(createUserDto.toDso(new User()));
        return new UserDto(newUser);
    }

    @PostMapping("/login")
    public AuthenticationResponseDto login(@RequestBody @Valid SignInUserDto signInUserDto) throws Exception {
        try {
            AuthenticationResponse authenticationResponse = authenticationService.verify(signInUserDto.toDso(new User()));

            String accessToken = authenticationResponse.getAccessToken();
            String refreshToken = jwtService.generateRefreshToken(accessToken);

            return new AuthenticationResponseDto(accessToken, refreshToken);
        } catch (AuthenticationException authenticationException) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, authenticationException.getMessage());
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user")
    public UserDto getUserByAccessToken(@AuthenticationPrincipal LoginUser loginUser) {
        return new UserDto(loginUser.getUser());
    }

    @PostMapping("/refresh-token")
    public AuthenticationResponseDto refreshToken(@RequestBody @Valid RefreshAccessTokenDto refreshAccessTokenDto) {
        RefreshToken token = jwtService.findRefreshTokenByRefreshToken(refreshAccessTokenDto.getRefreshToken());
        String accessToken = jwtService.generateAccessToken(token.getUser().getUsername());
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

        LoginUser loginUser = (LoginUser) applicationContext.getBean(UserDetailsService.class).loadUserByUsername(jwtService.extractUsername(accessToken));
        jwtService.revokeRefreshToken(loginUser.getUser()); // Invalidate refresh tokens on logout
    }

    @GetMapping("/test")
    public String test(@AuthenticationPrincipal LoginUser loginUser) {
        System.out.println(loginUser.getUser());
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "test");
//        return loginUser.getUser().getUsername();
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin(@AuthenticationPrincipal LoginUser loginUser) {
        return "test";
    }
}