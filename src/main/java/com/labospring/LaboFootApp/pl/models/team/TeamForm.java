package com.labospring.LaboFootApp.pl.models.team;

import com.labospring.LaboFootApp.bll.service.models.TeamBusiness;
import com.labospring.LaboFootApp.pl.models.coach.CoachForm;
import com.labospring.LaboFootApp.pl.models.player.PlayerForm;

import java.util.List;

public record TeamForm(
        String name,
        CoachForm coachForm,
        List<PlayerForm> playerDTOList
) {
    public TeamBusiness toTeamBusiness() {
        return new TeamBusiness(name,
                coachForm.toCoachBusiness(),
                playerDTOList.stream().map(PlayerForm::toPlayerBusiness).toList());
    }
}
