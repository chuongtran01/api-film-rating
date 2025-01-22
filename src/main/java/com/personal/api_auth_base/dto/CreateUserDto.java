package com.personal.api_auth_base.dto;

import com.personal.api_auth_base.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserDto implements IDsoTo<User> {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Override
    public User toDso(User dso) {
        if (dso == null) {
            return null;
        }

        dso.setUsername(username);
        dso.setPassword(password);
        dso.setFirstName(firstName);
        dso.setLastName(lastName);
        dso.setActive(true);

        return dso;
    }
}
