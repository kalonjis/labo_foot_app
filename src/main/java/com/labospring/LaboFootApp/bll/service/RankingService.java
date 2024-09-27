package com.labospring.LaboFootApp.bll.service;


import com.labospring.LaboFootApp.bll.service.models.RankingBusiness;
import com.labospring.LaboFootApp.bll.service.models.RankingEditBusiness;
import com.labospring.LaboFootApp.dl.entities.Ranking;
import com.labospring.LaboFootApp.dl.entities.Team;
import com.labospring.LaboFootApp.dl.entities.Tournament;

public interface RankingService extends BaseService<Long, Ranking, RankingBusiness>{
    void update(Long id, RankingEditBusiness entityBusiness);
    void updateNumGroup(Ranking ranking, int numGroup);
    Long createOne(Tournament tournament, Team team);

}
