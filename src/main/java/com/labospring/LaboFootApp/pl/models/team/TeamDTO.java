package com.labospring.LaboFootApp.pl.models.team;

import com.labospring.LaboFootApp.dl.entities.Team;
import com.labospring.LaboFootApp.pl.models.coach.CoachDTO;
import com.labospring.LaboFootApp.pl.models.player.PlayerDTO;

import java.util.List;

public record TeamDTO(
        Long id,
        String name,
        CoachDTO coach,
        List<PlayerDTO> players
) {
    public static TeamDTO fromEntity(Team team) {
        return new TeamDTO(
                team.getId(),
                team.getName(),
                CoachDTO.fromEntity(team.getCoach()),
                team.getPlayers().stream().map(PlayerDTO::fromEntity).toList());
    }
}
