package com.personal.api_auth_base.dto;

import com.personal.api_auth_base.model.Role;
import com.personal.api_auth_base.model.User;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDto implements IDsoFrom<User> {
    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private boolean active;
    private List<String> roles;

    public UserDto(User dso) {
        this.fromDso(dso);
    }

    @Override
    public IDsoFrom<User> fromDso(User dso) {
        if (dso == null) {
            return null;
        }
        this.userId = dso.getUserId();
        this.username = dso.getUsername();
        this.firstName = dso.getFirstName();
        this.lastName = dso.getLastName();
        this.active = dso.isActive();
        this.roles = dso.getRoles().stream().map(Role::getName).collect(Collectors.toList());

        return null;
    }
}
