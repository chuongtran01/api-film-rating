package com.personal.api_film_rating.controller;

import com.personal.api_film_rating.dto.MyAccountDto;
import com.personal.api_film_rating.dto.UpdateUserDto;
import com.personal.api_film_rating.dto.UserDto;
import com.personal.api_film_rating.entity.JwtUserPrincipal;
import com.personal.api_film_rating.entity.Role;
import com.personal.api_film_rating.entity.User;
import com.personal.api_film_rating.enums.EnumGender;
import com.personal.api_film_rating.mapper.UserMapper;
import com.personal.api_film_rating.service.UserService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     * Get my account
     * 
     * @param loginUser
     * @return UserDto
     */
    @GetMapping("/my-account")
    public UserDto getMyAccount(@AuthenticationPrincipal JwtUserPrincipal loginUser) {
        return userMapper.toUserDto(userService.findByUserId(UUID.fromString(loginUser.getId())));
    }

    /**
     * Update my account
     * 
     * @param myAccountDto
     * @param loginUser
     * @return UserDto
     */
    @PutMapping("/my-account")
    public UserDto updateAccountInformation(@RequestBody MyAccountDto myAccountDto,
            @AuthenticationPrincipal JwtUserPrincipal loginUser) {
        User user = userService.findByUserId(UUID.fromString(loginUser.getId()));
        user.setDob(myAccountDto.dob());
        user.setGender(myAccountDto.gender() != null ? EnumGender.valueOf(myAccountDto.gender()) : null);
        user.setDisplayName(myAccountDto.displayName());

        return userMapper.toUserDto(userService.saveUser(user));
    }

    /**
     * Find users by role
     * 
     * @param role
     * @param gender
     * @param displayName
     * @param email
     * @param createdAt
     * @param updatedAt
     * @param active
     * @param pageable
     * @return ResponseEntity<PagedModel<UserDto>>
     */
    @GetMapping
    public ResponseEntity<PagedModel<UserDto>> findUsersByRole(
            @RequestParam(required = false) List<String> role,
            @RequestParam(required = false) List<String> gender,
            @RequestParam(required = false) String displayName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Boolean active,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal JwtUserPrincipal loginUser) {
        if (!loginUser.isAdmin() && !loginUser.isModerator()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new PagedModel<>(
                        userService.findUsers(role, gender, displayName, email, active, pageable)
                                .map(userMapper::toUserDto)));
    }

    /**
     * Inactive user by id
     * 
     * @param id
     * @param loginUser
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inactiveUserById(@PathVariable(name = "id") String id,
            @AuthenticationPrincipal JwtUserPrincipal loginUser) {
        if (!loginUser.isAdmin() && !loginUser.isModerator()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userService.findByUserId(UUID.fromString(id));
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (user.getRole().getName().equals("ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        userService.inactiveUserById(UUID.fromString(id));
        return ResponseEntity.ok().build();
    }

    /**
     * Update user
     * 
     * @param id
     * @param updateUserDto
     * @param loginUser
     * @return ResponseEntity<UserDto>
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name = "id") String id,
            @RequestBody @Valid UpdateUserDto updateUserDto,
            @AuthenticationPrincipal JwtUserPrincipal loginUser) {
        if (!loginUser.isAdmin() && !loginUser.isModerator()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userService.findByUserId(UUID.fromString(id));
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        user.setDisplayName(updateUserDto.displayName());
        user.setEmail(updateUserDto.email());
        user.setDob(updateUserDto.dob());
        user.setGender(updateUserDto.gender() != null && !updateUserDto.gender().isBlank()
                ? EnumGender.valueOf(updateUserDto.gender())
                : null);
        user.setActive(updateUserDto.active());

        Role role = userService.findRoleByName(updateUserDto.role());
        user.setRole(role);

        return ResponseEntity.ok(userMapper.toUserDto(userService.saveUser(user)));
    }
}
