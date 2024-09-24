package com.labospring.LaboFootApp.pl.models.tournament;

import com.labospring.LaboFootApp.dl.entities.Address;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.enums.TournamentType;

import java.time.LocalDateTime;

public record TournamentDTO(
        Long id,
        String title,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String placeName,
        Address address,
        TournamentType tournamentType,
        boolean isClose) {

    public static TournamentDTO fromEntity(Tournament tournament){
        return new TournamentDTO(
                tournament.getId(),
                tournament.getTitle(),
                tournament.getStartDate(),
                tournament.getEndDate(),
                tournament.getPlaceName(),
                tournament.getAddress(),
                tournament.getTournamentType(),
                tournament.isClose()
        );
    }
}
