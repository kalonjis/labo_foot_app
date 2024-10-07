package com.labospring.LaboFootApp.pl.models.tournament;

import com.labospring.LaboFootApp.bll.service.models.TournamentBusiness;
import com.labospring.LaboFootApp.dl.entities.Address;
import com.labospring.LaboFootApp.dl.enums.TournamentStatus;
import com.labospring.LaboFootApp.dl.enums.TournamentType;
import com.labospring.LaboFootApp.il.annotation.ValidTournamentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TournamentForm(
        @NotNull @NotBlank
        String title,
        LocalDateTime startDate,
        LocalDateTime endDate,
        @NotNull @NotBlank
        String placeName,
        @NotNull
        Address address,
        @NotNull
        TournamentType tournamentType,
        @NotNull
        @ValidTournamentStatus
        TournamentStatus tournamentStatus
) {

    public TournamentBusiness toTournamentBusiness(){
        return new TournamentBusiness(
                title,
                startDate,
                endDate,
                placeName,
                address,
                tournamentType,
                tournamentStatus
        );
    }
}
