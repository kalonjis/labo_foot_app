package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.bll.service.models.ParticipatingTeamBusiness;
import com.labospring.LaboFootApp.dl.entities.ParticipatingTeam;
import com.labospring.LaboFootApp.dl.enums.SubscriptionStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ParticipatingTeamService {
    List<ParticipatingTeam> getAll();
    List<ParticipatingTeam> getAllTeamsByTournament(Long tournamentId);
    List<ParticipatingTeam> getAllByTournamentAndStatus(Long tournamentId, SubscriptionStatus status);
    ParticipatingTeam getOneById(ParticipatingTeam.ParticipatingTeamId id);
    ParticipatingTeam.ParticipatingTeamId createOne(ParticipatingTeamBusiness ptb);
    void deleteById(ParticipatingTeam.ParticipatingTeamId id);
    void changeStatus(ParticipatingTeam.ParticipatingTeamId id, SubscriptionStatus status);
    void changeStatusToCanceled(ParticipatingTeam.ParticipatingTeamId id);
    void dispatchTeamsToGroups(Long tournamentId);
    void dispatchTeamsToBrackets(Long tournamentId);
}
