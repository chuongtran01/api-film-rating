package com.personal.api_auth_base.controller;

import com.personal.api_auth_base.dto.*;
import com.personal.api_auth_base.model.AuthenticationResponse;
import com.personal.api_auth_base.model.LoginUser;
import com.personal.api_auth_base.model.RefreshToken;
import com.personal.api_auth_base.model.User;
import com.personal.api_auth_base.service.AuthenticationService;
import com.personal.api_auth_base.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    private final JwtService jwtService;

    public AuthController(AuthenticationService authenticationService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody @Valid CreateUserDto createUserDto) {
        User newUser = authenticationService.register(createUserDto.toDso(new User()));
        return new UserDto(newUser);
    }

    @PostMapping("/login")
    public AuthenticationResponseDto login(@RequestBody @Valid SignInUserDto signInUserDto) {
        AuthenticationResponse authenticationResponse = authenticationService.verify(signInUserDto.toDso(new User()));

        if (authenticationResponse.getStatus().equals(HttpStatus.OK)) {
            String accessToken = authenticationResponse.getAccessToken();
            String refreshToken = jwtService.generateRefreshToken(accessToken);

            return new AuthenticationResponseDto(accessToken, refreshToken, new UserDto(authenticationResponse.getUser()));
        }
        // Should never reach here since exception has been thrown
        return new AuthenticationResponseDto(null, null, null);
    }

    @GetMapping("/refresh-token")
    public AuthenticationResponseDto refreshToken(@RequestBody @Valid RefreshAccessTokenDto refreshAccessTokenDto) {
        RefreshToken token = jwtService.findRefreshTokenByRefreshToken(refreshAccessTokenDto.getRefreshToken());
        String accessToken = jwtService.generateAccessToken(token.getUser().getUsername());
        return new AuthenticationResponseDto(accessToken, null, null);
    }

    @GetMapping("/logout")
    public void logout(@RequestBody @Valid LogoutDto logoutDto, @AuthenticationPrincipal LoginUser loginUser) {
        jwtService.blacklistAccessToken(logoutDto.getAccessToken()); // Blacklist access token in redis
        jwtService.revokeRefreshToken(loginUser.getUser()); // Invalidate refresh tokens on logout
    }

    @GetMapping("/test")
    public String test(@AuthenticationPrincipal LoginUser loginUser) {
        System.out.println(loginUser.getUser());
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        return loginUser.getUser().getUsername();
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin(@AuthenticationPrincipal LoginUser loginUser) {
        return "test";
    }
}