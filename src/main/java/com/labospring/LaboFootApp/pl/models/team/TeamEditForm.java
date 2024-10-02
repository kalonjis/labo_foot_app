package com.labospring.LaboFootApp.pl.models.team;

import com.labospring.LaboFootApp.bll.service.models.team.TeamBusiness;
import com.labospring.LaboFootApp.pl.models.coach.CoachForm;

public record TeamEditForm(String name,
                           CoachForm coach) {
    public TeamBusiness toTeamBusiness() {
        return new TeamBusiness(name,
                coach.toCoachBusiness(),
                null);
    }
}
