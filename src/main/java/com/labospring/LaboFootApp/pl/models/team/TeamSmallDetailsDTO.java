package com.labospring.LaboFootApp.pl.models.team;

import com.labospring.LaboFootApp.dl.entities.Team;

public record TeamSmallDetailsDTO(Long id,
                                  String name) {

    public static TeamSmallDetailsDTO fromEntity(Team team) {
        return new TeamSmallDetailsDTO(team.getId(), team.getName());
    }
}
