package com.labospring.LaboFootApp.pl.models.participatingTeam;

import com.labospring.LaboFootApp.bll.service.models.ParticipatingTeamBusiness;
import com.labospring.LaboFootApp.dl.entities.Tournament;
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
