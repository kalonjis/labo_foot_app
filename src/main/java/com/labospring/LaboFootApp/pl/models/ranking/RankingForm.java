package com.labospring.LaboFootApp.pl.models.ranking;

import com.labospring.LaboFootApp.bll.service.models.ranking.RankingBusiness;

public record RankingForm(
        Long tournament_id,
        int numGroup
        ){
    public RankingBusiness toRankingBusiness(){
        return new RankingBusiness(tournament_id, numGroup);
    }
}
