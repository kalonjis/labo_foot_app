package com.labospring.LaboFootApp.bll.service;


import com.labospring.LaboFootApp.bll.service.models.RankingBusiness;
import com.labospring.LaboFootApp.bll.service.models.RankingEditBusiness;
import com.labospring.LaboFootApp.dl.entities.Ranking;
import com.labospring.LaboFootApp.dl.entities.Team;
import com.labospring.LaboFootApp.dl.entities.Tournament;

import java.util.List;

public interface RankingService extends BaseService<Long, Ranking, RankingBusiness>{
    Long createOne(Tournament tournament, Team team);
    Ranking getByTournamentIdAndTeamId(Long tournamentId, Long TeamId);
    List<Ranking> getAllByTournamentIdAndNumGroup(Long tournamentId, int numGroup);
    void update(Long id, RankingEditBusiness entityBusiness);
    void updateNumGroup(Ranking ranking, int numGroup);
    void updateWinnerRanking(Ranking ranking);
    void updateLooserRanking(Ranking ranking);
    void updateDrawerRanking(Ranking ranking);
    void updateNbMatchPlayed(Ranking ranking);
    void updateGoalsFor(Ranking ranking, int goals);
    void updateGoalsAgainst(Ranking ranking, int goals);
    void updatePosition(Ranking ranking);
}
