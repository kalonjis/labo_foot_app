package com.labospring.LaboFootApp.pl.models.participatingTeam;

import com.labospring.LaboFootApp.bll.service.models.participatingteam.ParticipatingTeamBusiness;
import jakarta.validation.constraints.NotNull;

public record ParticipatingTeamForm(
        @NotNull
        Long tournamentId,
        @NotNull
        Long teamId

) {
    public ParticipatingTeamBusiness toParticipatingTeamBusiness(){
        return new ParticipatingTeamBusiness(tournamentId, teamId);
    }
}
