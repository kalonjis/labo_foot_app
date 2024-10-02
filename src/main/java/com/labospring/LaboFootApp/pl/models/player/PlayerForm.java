package com.labospring.LaboFootApp.pl.models.player;

import com.labospring.LaboFootApp.bll.service.models.matchactor.PlayerBusiness;
import com.labospring.LaboFootApp.dl.enums.FieldPosition;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PlayerForm(String firstname,
                         @NotNull @NotBlank
                         String lastname,
                         @NotNull @NotBlank
                         String playername,
                         @NotNull
                         Integer teamNumber,
                         FieldPosition fieldPosition,
                         Long teamID) {

    public PlayerBusiness toPlayerBusiness() {
        return new PlayerBusiness(firstname, lastname, playername, teamNumber, fieldPosition, teamID);
    }
}
