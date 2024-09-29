package com.labospring.LaboFootApp.pl.models.user;

import com.labospring.LaboFootApp.dl.entities.User;

public record UserSmallDTO(Long id, String username) {
    public static UserSmallDTO fromEntity(User user){
        return new UserSmallDTO(user.getId(), user.getUsername());
    }
}
