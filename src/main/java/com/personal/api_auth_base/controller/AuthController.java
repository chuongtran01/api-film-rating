package com.personal.api_auth_base.controller;

import com.personal.api_auth_base.dto.AuthenticationDto;
import com.personal.api_auth_base.dto.CreateUserDto;
import com.personal.api_auth_base.dto.SignInUserDto;
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
    public User register(@RequestBody @Valid CreateUserDto createUserDto) {
        return authenticationService.register(createUserDto.toDso(new User()));
    }

    @PostMapping("/login")
    public AuthenticationDto login(@RequestBody @Valid SignInUserDto signInUserDto) {
        String accessToken = authenticationService.verify(signInUserDto.toDso(new User()));

        String refreshToken = jwtService.generateRefreshToken(accessToken);

        return new AuthenticationDto(accessToken, refreshToken);
    }

    @GetMapping("/refresh-token")
    public AuthenticationDto refreshToken(@RequestParam(name = "refresh-token") String refreshToken) throws Exception {

        RefreshToken token = jwtService.findRefreshTokenByRefreshToken(refreshToken);

        if (token == null || token.isRevoked() || jwtService.isTokenExpired(token.getToken())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String accessToken = jwtService.generateAccessToken(token.getUser().getUsername());
        return new AuthenticationDto(accessToken, null);
    }

    @GetMapping("/logout")
    public void logout(@RequestParam(name = "access-token") String accessToken, @AuthenticationPrincipal LoginUser loginUser) {
        // Blacklist access token in redis
        jwtService.blacklistAccessToken(accessToken);

        // Invalidate refresh tokens on logout
        jwtService.revokeRefreshToken(loginUser.getUser());
    }

    @GetMapping("/test")
    public String test(@AuthenticationPrincipal LoginUser loginUser) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
//        return loginUser.getUser().getUsername();
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin(@AuthenticationPrincipal LoginUser loginUser) {
        return "test";
    }
}
