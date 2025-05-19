package com.personal.api_film_rating.controller;

import com.personal.api_film_rating.dto.MyAccountDto;
import com.personal.api_film_rating.dto.UserDto;
import com.personal.api_film_rating.entity.JwtUserPrincipal;
import com.personal.api_film_rating.entity.User;
import com.personal.api_film_rating.enums.EnumGender;
import com.personal.api_film_rating.mapper.UserMapper;
import com.personal.api_film_rating.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public UserDto getMyAccount(@AuthenticationPrincipal JwtUserPrincipal loginUser) {
        return userMapper.toUserDto(userService.findByUserId(UUID.fromString(loginUser.getId())));
    }

    @PutMapping("/my-account")
    public UserDto updateAccountInformation(@RequestBody MyAccountDto myAccountDto, @AuthenticationPrincipal JwtUserPrincipal loginUser) {
        User user = userService.findByUserId(UUID.fromString(loginUser.getId()));
        user.setDob(myAccountDto.dob());
        user.setGender(myAccountDto.gender() != null ? EnumGender.valueOf(myAccountDto.gender()) : null);
        user.setDisplayName(myAccountDto.displayName());

        return userMapper.toUserDto(userService.save(user));
    }
}
