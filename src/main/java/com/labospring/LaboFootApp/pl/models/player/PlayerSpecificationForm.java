package com.labospring.LaboFootApp.pl.models.player;

import com.labospring.LaboFootApp.dl.entities.Player;
import com.labospring.LaboFootApp.dl.enums.FieldPosition;

public record PlayerSpecificationForm (String playerName,
                                       String firstname,
                                       String lastname,
                                       Integer teamNumber,
                                       FieldPosition fieldPosition
) {
    public Player toEntity(){
        return new Player(playerName, firstname, lastname, teamNumber, fieldPosition);
    }
}