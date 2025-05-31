package com.personal.api_film_rating.controller;

import com.personal.api_film_rating.dto.*;
import com.personal.api_film_rating.entity.*;
import com.personal.api_film_rating.mapper.UserMapper;
import com.personal.api_film_rating.service.AuthenticationService;
import com.personal.api_film_rating.service.JwtService;
import com.personal.api_film_rating.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserService userService;

    private final ApplicationContext applicationContext;

    private final UserMapper userMapper;

    public AuthController(AuthenticationService authenticationService, JwtService jwtService,
            ApplicationContext applicationContext, UserMapper userMapper, UserService userService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
        this.applicationContext = applicationContext;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    /**
     * Register a new user
     * 
     * @param signUpDto
     * @return UserDto
     */
    @PostMapping("/register")
    public UserDto register(@RequestBody @Valid SignUpDto signUpDto) {
        User newUser = authenticationService.register(userMapper.toUser(signUpDto));
        return userMapper.toUserDto(newUser);
    }

    /**
     * Login a user
     * 
     * @param signInDto
     * @return AuthenticationResponseDto
     */
    @PostMapping("/login")
    public AuthenticationResponseDto login(@RequestBody @Valid SignInDto signInDto) throws Exception {
        try {
            AuthenticationResponse authenticationResponse = authenticationService.verify(signInDto.getEmail(),
                    signInDto.getPassword());

            String accessToken = authenticationResponse.getAccessToken();
            String refreshToken = authenticationResponse.getRefreshToken();

            return new AuthenticationResponseDto(accessToken, refreshToken);
        } catch (com.personal.api_film_rating.exceptions.AuthenticationException authenticationException) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, authenticationException.getMessage());
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Refresh a token
     * 
     * @param refreshAccessTokenDto
     * @return AuthenticationResponseDto
     */
    @PostMapping("/refresh-token")
    public AuthenticationResponseDto refreshToken(@RequestBody @Valid RefreshAccessTokenDto refreshAccessTokenDto) {
        RefreshToken token = jwtService.findRefreshTokenByRefreshToken(refreshAccessTokenDto.getRefreshToken());
        String accessToken = jwtService.generateAccessToken(token.getUser().getEmail());
        return new AuthenticationResponseDto(accessToken, null);
    }

    /**
     * Logout a user
     * 
     * @param request
     * @return Void
     */
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

    /**
     * Change password
     * 
     * @param changePasswordDto
     * @param loginUser
     * @return ResponseEntity<UserDto>
     */
    @PostMapping("/change-password")
    public ResponseEntity<UserDto> changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto,
            @AuthenticationPrincipal JwtUserPrincipal loginUser) {
        try {
            if (!changePasswordDto.newPassword().equals(changePasswordDto.confirmPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            authenticationService.verify(loginUser.getEmail(), changePasswordDto.currentPassword());
            User existingUser = userService.findByUserId(UUID.fromString(loginUser.getId()));

            return ResponseEntity
                    .ok()
                    .body(userMapper.toUserDto(
                            authenticationService.changePassword(existingUser, changePasswordDto.newPassword())));
        } catch (AuthenticationException authenticationException) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, authenticationException.getMessage());
        }
    }
}