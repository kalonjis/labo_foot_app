package com.labospring.LaboFootApp.bll.service.models.ranking;

public record RankingEditBusiness(
        Integer nbWins,
        Integer nbLosses,
        Integer nbDraws,
        Integer goalsFor,
        Integer goalsAgainst
) {
}

