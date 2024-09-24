package com.labospring.LaboFootApp.pl.models.ranking;

import com.labospring.LaboFootApp.dl.entities.Ranking;
import com.labospring.LaboFootApp.dl.entities.Team;
import com.labospring.LaboFootApp.dl.entities.Tournament;

public record RankingDTO(
        Long id,
        Team team,
        Tournament tournament,
        int numGroup,
        int rankingPosition,
        int totalPoints,
        int nbMatchPlayed,
        int nbWins,
        int nbLosses,
        int nbDraws,
        int goalsFor,
        int goalsAgainst,
        int goalsDiff
) {
    public static RankingDTO fromEntity(Ranking ranking) {
        return new RankingDTO(
                ranking.getId(),
                ranking.getTeam(),
                ranking.getTournament(),
                ranking.getNumGroup(),
                ranking.getRankingPosition(),
                ranking.getTotalPoints(),
                ranking.getNbMatchPlayed(),
                ranking.getNbWins(),
                ranking.getNbLosses(),
                ranking.getNbDraws(),
                ranking.getGoalsFor(),
                ranking.getGoalsAgainst(),
                ranking.getGoalsDiff()
        );
    }

}
