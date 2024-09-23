package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.bll.service.models.FootMatchBusiness;
import com.labospring.LaboFootApp.bll.service.models.ScoreBusiness;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.dl.enums.MatchStatus;

public interface FootMatchService extends BaseService<Long, FootMatch, FootMatchBusiness> {
    void changeStatus(Long id, MatchStatus matchStatus);
    void changeScore(Long id, ScoreBusiness scoreBusiness);
    void changeModerator(Long id, Long moderatorId);
}
