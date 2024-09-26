package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.bll.service.models.ParticipatingTeamBusiness;
import com.labospring.LaboFootApp.dl.entities.ParticipatingTeam;
import com.labospring.LaboFootApp.dl.enums.SubscriptionStatus;
import com.labospring.LaboFootApp.pl.models.participatingTeam.ParticipatingTeamStatusForm;

public interface ParticipatingTeamService extends BaseService<Long, ParticipatingTeam, ParticipatingTeamBusiness> {
    ParticipatingTeam getOneById(ParticipatingTeam.ParticipatingTeamId id);
    ParticipatingTeam.ParticipatingTeamId createOne(ParticipatingTeamBusiness ptb);
    void deleteById(ParticipatingTeam.ParticipatingTeamId id);
    void changeStatus(ParticipatingTeam.ParticipatingTeamId id, SubscriptionStatus status);
}
