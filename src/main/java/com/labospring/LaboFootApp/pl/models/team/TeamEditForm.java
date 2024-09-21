package com.labospring.LaboFootApp.pl.models.team;

import com.labospring.LaboFootApp.bll.service.models.TeamBusiness;
import com.labospring.LaboFootApp.pl.models.coach.CoachForm;
import com.labospring.LaboFootApp.pl.models.player.PlayerForm;

public record TeamEditForm(String name,
                           CoachForm coach) {
    public TeamBusiness toTeamBusiness() {
        return new TeamBusiness(name,
                coach.toCoachBusiness(),
                null);
    }
}
