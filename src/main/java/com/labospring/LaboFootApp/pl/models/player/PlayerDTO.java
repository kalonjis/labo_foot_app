package com.labospring.LaboFootApp.pl.models.player;

import com.labospring.LaboFootApp.dl.entities.Player;
import com.labospring.LaboFootApp.dl.enums.FieldPosition;

public record PlayerDTO(String firstname,
                        String lastname,
                        String playername,
                        Integer teamNumber,
                        FieldPosition fieldPosition) {

    public static PlayerDTO fromEntity(Player player) {
        return new PlayerDTO(
                player.getFirstname(), player.getLastname(),
                player.getPlayerName(), player.getTeamNumber(),
                player.getFieldPosition());
    }

}
