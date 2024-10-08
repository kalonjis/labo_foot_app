package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.bll.service.models.FootMatchEditBusiness;
import com.labospring.LaboFootApp.bll.service.models.FootMatchSpecificationDTO;
import com.labospring.LaboFootApp.bll.service.models.ScoreBusiness;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.enums.MatchStatus;

import java.util.List;

public interface FootMatchService extends BaseService<Long, FootMatch, FootMatchEditBusiness>, BaseByUser<FootMatch> {
    void changeStatus(Long id, MatchStatus matchStatus);
    void changeScore(Long id, ScoreBusiness scoreBusiness);
    void changeModerator(Long id, Long moderatorId);

    List<FootMatch> getByCriteria(FootMatchSpecificationDTO footMatch);
    void buildChampionshipCalendar(Long tournamentId);
    void buildGroupsCalendar(Long tournamentId);
}
