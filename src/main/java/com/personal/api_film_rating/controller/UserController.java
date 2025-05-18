package com.personal.api_film_rating.controller;

import com.personal.api_film_rating.dto.UserDto;
import com.personal.api_film_rating.entity.JwtUserPrincipal;
import com.personal.api_film_rating.mapper.UserMapper;
import com.personal.api_film_rating.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @GetMapping("/my-account")
    public UserDto findPersonalInfo(@AuthenticationPrincipal JwtUserPrincipal loginUser) {
        return userMapper.toUserDto(userService.findByUserId(UUID.fromString(loginUser.getId())));
    }
}
