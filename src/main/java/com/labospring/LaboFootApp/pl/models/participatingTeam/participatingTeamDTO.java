package com.labospring.LaboFootApp.pl.models.participatingTeam;

import com.labospring.LaboFootApp.dl.entities.ParticipatingTeam;
import com.labospring.LaboFootApp.dl.enums.SubscriptionStatus;
import com.labospring.LaboFootApp.pl.models.team.TeamSmallDetailsDTO;
import com.labospring.LaboFootApp.pl.models.tournament.TournamentSmallDetailsDTO;

public record participatingTeamDTO(
        TournamentSmallDetailsDTO tournament,
        TeamSmallDetailsDTO team,
        SubscriptionStatus subscriptionStatus
) {
    public static participatingTeamDTO fromEntity(ParticipatingTeam pt){
        return new participatingTeamDTO(
                TournamentSmallDetailsDTO.fromEntity(pt.getTournament()),
                TeamSmallDetailsDTO.fromEntity(pt.getTeam()),
                pt.getSubscriptionStatus()
                );
    }
}
