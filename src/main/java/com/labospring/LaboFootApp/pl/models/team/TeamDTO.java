package com.labospring.LaboFootApp.pl.models.team;

import com.labospring.LaboFootApp.dl.entities.Team;
import com.labospring.LaboFootApp.pl.models.player.PlayerDTO;

import java.util.List;

public record TeamDTO(
        String name,
        String coachName,
        List<PlayerDTO> playerDTOList
) {
    public static TeamDTO fromEntity(Team team) {
        return new TeamDTO(team.getName(),
                team.getCoach().getFirstname() + " " + team.getCoach().getLastname(),
                team.getPlayers().stream().map(PlayerDTO::fromEntity).toList());
    }
}
