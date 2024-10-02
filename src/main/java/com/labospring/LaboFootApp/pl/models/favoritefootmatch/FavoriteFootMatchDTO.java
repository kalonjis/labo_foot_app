package com.labospring.LaboFootApp.pl.models.favoritefootmatch;

import com.labospring.LaboFootApp.dl.entities.FavoriteFootMatch;

public record FavoriteFootMatchDTO(Long footMatchId, boolean notification) {
    public static FavoriteFootMatchDTO fromEntity(FavoriteFootMatch favorite){
        return new FavoriteFootMatchDTO(favorite.getFootMatch().getId(),
                favorite.isNotificationActivated());
    }
}
