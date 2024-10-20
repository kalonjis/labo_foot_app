package com.labospring.LaboFootApp.pl.models.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserSearchForm(
        @NotBlank @NotNull
        String username,
        @Email
        String email
        ) { }
