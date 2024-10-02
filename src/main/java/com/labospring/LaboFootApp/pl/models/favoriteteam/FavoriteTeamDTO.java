package com.labospring.LaboFootApp.pl.models.favoriteteam;

import com.labospring.LaboFootApp.dl.entities.FavoriteTeam;

public record FavoriteTeamDTO(Long teamId, boolean notification) {
    public static FavoriteTeamDTO fromEntity(FavoriteTeam favorite){
        return new FavoriteTeamDTO(favorite.getTeam().getId(),
                favorite.isNotificationActivated());
    }
}
