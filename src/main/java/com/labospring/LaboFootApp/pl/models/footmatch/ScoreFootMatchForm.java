package com.labospring.LaboFootApp.pl.models.footmatch;

import com.labospring.LaboFootApp.bll.service.models.ScoreBusiness;

public record ScoreFootMatchForm(int scoreTeamHome, int scoreTeamAway) {
    public ScoreBusiness toScoreBusiness(){
        return new ScoreBusiness(scoreTeamHome, scoreTeamAway);
    }
}
