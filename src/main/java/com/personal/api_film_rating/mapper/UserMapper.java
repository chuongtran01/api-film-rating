package com.personal.api_film_rating.mapper;

import com.personal.api_film_rating.dto.SignUpDto;
import com.personal.api_film_rating.dto.UserDto;
import com.personal.api_film_rating.entity.Role;
import com.personal.api_film_rating.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(SignUpDto signUpDto);

    @Mapping(target = "role", source = "role")
    UserDto toUserDto(User user);

    default String map(Role role) {
        return role != null ? role.getName() : null;
    }
}
