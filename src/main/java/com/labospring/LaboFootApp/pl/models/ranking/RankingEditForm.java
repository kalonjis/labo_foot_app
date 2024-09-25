package com.labospring.LaboFootApp.pl.models.ranking;

import com.labospring.LaboFootApp.bll.service.models.RankingEditBusiness;

public record RankingEditForm(
        Integer nbWins,
        Integer nbLosses,
        Integer nbDraws,
        Integer goalsFor,
        Integer goalsAgainst
) {
    public RankingEditBusiness toRankingEditBusiness(){
        return new RankingEditBusiness(nbWins, nbLosses, nbDraws, goalsFor, goalsAgainst);
    }
}
