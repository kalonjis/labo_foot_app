package com.labospring.LaboFootApp.pl.models.team;

import com.labospring.LaboFootApp.bll.service.models.team.TeamBusiness;
import com.labospring.LaboFootApp.pl.models.coach.CoachForm;
import com.labospring.LaboFootApp.pl.models.player.PlayerForm;

import java.util.List;

public record TeamForm(
        String name,
        CoachForm coach,
        List<PlayerForm> players
) {
    public TeamBusiness toTeamBusiness() {
        return new TeamBusiness(name,
                coach.toCoachBusiness(),
                players.stream().map(PlayerForm::toPlayerBusiness).toList());
    }
}
