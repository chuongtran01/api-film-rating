package com.personal.api_auth_base.dto;

import com.personal.api_auth_base.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInUserDto implements IDsoTo<User> {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    @Override
    public User toDso(User dso) {
        if (dso == null) {
            return null;
        }

        dso.setUsername(username);
        dso.setPassword(password);

        return dso;
    }
}
