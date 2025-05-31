package com.personal.api_film_rating.mapper;

import com.personal.api_film_rating.dto.SignUpDto;
import com.personal.api_film_rating.dto.UpdateUserDto;
import com.personal.api_film_rating.dto.UserDto;
import com.personal.api_film_rating.entity.Role;
import com.personal.api_film_rating.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "role", ignore = true)
    User toUser(SignUpDto signUpDto);

    @Mapping(target = "role", source = "role")
    UserDto toUserDto(User user);

    default String map(Role role) {
        return role != null ? role.getName() : null;
    }
}
