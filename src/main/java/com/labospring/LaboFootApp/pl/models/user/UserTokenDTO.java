package com.labospring.LaboFootApp.pl.models.user;

import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.dl.enums.Role;

public record UserTokenDTO (
        Long id,
        String username,
        Role role,
        String token
){
    public static UserTokenDTO fromEntity(User user, String token) {
        return new UserTokenDTO(user.getId(), user.getUsername(), user.getRole(), token);
    }
}
