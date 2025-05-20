package com.personal.api_film_rating.controller;

import com.personal.api_film_rating.dto.MyAccountDto;
import com.personal.api_film_rating.dto.UserDto;
import com.personal.api_film_rating.entity.JwtUserPrincipal;
import com.personal.api_film_rating.entity.User;
import com.personal.api_film_rating.enums.EnumGender;
import com.personal.api_film_rating.mapper.UserMapper;
import com.personal.api_film_rating.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/users")
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

    @GetMapping
    public ResponseEntity<PagedModel<UserDto>> findUsersByRole(
            @RequestParam(required = false) String role,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new PagedModel<>(userService.findUsersByRole(role, pageable).map(userMapper::toUserDto)));
    }
}
