package com.labospring.LaboFootApp.pl.models.footmatch.form;

import com.labospring.LaboFootApp.bll.service.models.footmatch.ScoreBusiness;
import jakarta.validation.constraints.Min;

public record FootMatchScoreForm(@Min(value=0)int scoreTeamHome,@Min(value=0) int scoreTeamAway) {
    public ScoreBusiness toScoreBusiness(){
        return new ScoreBusiness(scoreTeamHome, scoreTeamAway);
    }
}
