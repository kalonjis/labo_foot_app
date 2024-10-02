package com.labospring.LaboFootApp.bll.service;


import com.labospring.LaboFootApp.bll.service.models.ranking.RankingBusiness;
import com.labospring.LaboFootApp.bll.service.models.ranking.RankingEditBusiness;
import com.labospring.LaboFootApp.dl.entities.Ranking;
import com.labospring.LaboFootApp.dl.entities.Team;
import com.labospring.LaboFootApp.dl.entities.Tournament;

import java.util.List;

public interface RankingService extends BaseService<Long, Ranking, RankingBusiness> {
    Long createOne(Tournament tournament, Team team);
    Ranking getByTournamentIdAndTeamId(Long tournamentId, Long TeamId);
    List<Ranking> getAllByTournamentIdAndNumGroup(Long tournamentId, int numGroup);
    void update(Long id, RankingEditBusiness entityBusiness);
    void updateNumGroup(Ranking ranking, int numGroup);
    void updateGoalsFor(Ranking ranking, int goals);
    void updateGoalsAgainst(Ranking ranking, int goals);
    void updatePosition(Ranking ranking);
    void updateStartingMatch(Ranking rankingTeamHome, Ranking rankingTeamAway);
    void updateGettingWinner(Ranking winningRanking, Ranking losingRanking);
    void updateGettingDrawer(Ranking fromwinnerRanking, Ranking fromLoserRanking);
    void updateGettingWinnerFromLoser(Ranking winningRanking, Ranking losingRanking);
}
