package com.labospring.LaboFootApp.bll.service.models;

public record RankingEditBusiness(
        Integer nbWins,
        Integer nbLosses,
        Integer nbDraws,
        Integer goalsFor,
        Integer goalsAgainst
) {
}

