package com.labospring.LaboFootApp.pl.models.user;

import com.labospring.LaboFootApp.dl.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginForm(
        @NotBlank
        @Size(max = 50)
        String username,
        @NotBlank
        String password
) {
    public User toEntity(){
        return new User(username, password);
    }
}
