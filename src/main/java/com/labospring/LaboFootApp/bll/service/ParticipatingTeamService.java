package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.bll.service.models.ParticipatingTeamBusiness;
import com.labospring.LaboFootApp.dl.entities.ParticipatingTeam;
import com.labospring.LaboFootApp.dl.enums.SubscriptionStatus;

import java.util.List;

public interface ParticipatingTeamService {
    List<ParticipatingTeam> getAll();
    ParticipatingTeam getOneById(ParticipatingTeam.ParticipatingTeamId id);
    ParticipatingTeam.ParticipatingTeamId createOne(ParticipatingTeamBusiness ptb);
    void deleteById(ParticipatingTeam.ParticipatingTeamId id);
    void changeStatus(ParticipatingTeam.ParticipatingTeamId id, SubscriptionStatus status);
    void changeStatusToCanceled(ParticipatingTeam.ParticipatingTeamId id);
    void dispatchTeamsToGroups(Long tournamentId);
}
