package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.bll.service.models.footmatch.FootMatchEditBusiness;
import com.labospring.LaboFootApp.bll.service.models.footmatch.FootMatchSpecificationDTO;
import com.labospring.LaboFootApp.bll.service.models.footmatch.ScoreBusiness;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.enums.MatchStage;
import com.labospring.LaboFootApp.dl.enums.MatchStatus;

import java.util.List;

public interface FootMatchService extends BaseService<Long, FootMatch, FootMatchEditBusiness>, BaseByUser<FootMatch> {
    void changeStatus(Long id, MatchStatus matchStatus);
    void changeScore(Long id, ScoreBusiness scoreBusiness);
    void changeModerator(Long id, Long moderatorId);
    FootMatch buildMatchForBracket(Tournament tournament, MatchStage matchStage);
    List<FootMatch> getByCriteria(FootMatchSpecificationDTO footMatch);
}
