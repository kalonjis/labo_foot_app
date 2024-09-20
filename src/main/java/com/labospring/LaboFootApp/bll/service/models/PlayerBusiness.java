package com.labospring.LaboFootApp.bll.service.models;

import com.labospring.LaboFootApp.dl.entities.Player;
import com.labospring.LaboFootApp.dl.enums.FieldPosition;

public record PlayerBusiness(String firstname,
                             String lastname,
                             String playername,
                             int teamNumber,
                             FieldPosition fieldPosition,
                             Long teamID
                             ) {
    public Player toEntity() {
        return new Player(null, firstname, lastname,
                playername, teamNumber, fieldPosition);
    }
}
