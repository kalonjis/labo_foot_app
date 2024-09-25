package com.labospring.LaboFootApp.pl.models.tournament;

import com.labospring.LaboFootApp.dl.entities.Tournament;

public record TournamentSmallDetailsDTO(
        Long id,
        String name) {

    public static TournamentSmallDetailsDTO fromEntity(Tournament tournament) {
        return new TournamentSmallDetailsDTO(tournament.getId(), tournament.getTitle());
    }
}

