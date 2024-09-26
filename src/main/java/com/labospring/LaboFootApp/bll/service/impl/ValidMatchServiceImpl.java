package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.exceptions.BadStatusException;
import com.labospring.LaboFootApp.bll.exceptions.IncorrectDateException;
import com.labospring.LaboFootApp.bll.exceptions.NotInTournamentException;
import com.labospring.LaboFootApp.bll.exceptions.SameTeamException;
import com.labospring.LaboFootApp.bll.service.ValidMatchService;
import com.labospring.LaboFootApp.dal.repositories.ParticipatingTeamRepository;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.Team;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.enums.SubscriptionStatus;
import com.labospring.LaboFootApp.dl.enums.TournamentStatus;
import com.labospring.LaboFootApp.il.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidMatchServiceImpl implements ValidMatchService {
    private final ParticipatingTeamRepository participatingTeamRepository; // TODO Service

    @Override
    public boolean isValid(FootMatch footMatch) {
        Tournament tournament = footMatch.getTournament();
        if(!canBuildMatch(tournament))
            throw new BadStatusException("Status Tournament need to be PENDING or STARTED");

        if(isSameTeam(footMatch.getTeamHome(), footMatch.getTeamAway()))
            throw new SameTeamException("The match can't have a team against each other");

        if(!participatingTeamRepository.existByTeamAndTournamentAndStatus(footMatch.getTeamHome(), footMatch.getTournament(), SubscriptionStatus.ACCEPTED) )
            throw new NotInTournamentException("Team " + footMatch.getTeamHome().getName() + " not in the tournament");

        if(!participatingTeamRepository.existByTeamAndTournamentAndStatus(footMatch.getTeamAway(), footMatch.getTournament(), SubscriptionStatus.ACCEPTED))
            throw new NotInTournamentException("Team " + footMatch.getTeamAway().getName() + " not in the tournament");

        if (!DateUtils.isDateBetween(footMatch.getMatchDateTime(),
                tournament.getStartDate(), tournament.getEndDate()) )
            throw new IncorrectDateException("The match date have to be between the tournament date (" + tournament.getStartDate() + "," + tournament.getEndDate());

        return true;
    }

    public boolean canBuildMatch(Tournament tournament){

        return tournament.getTournamentStatus().equals(TournamentStatus.PENDING) ||
                tournament.getTournamentStatus().equals(TournamentStatus.STARTED);
    }

    public boolean isSameTeam(Team home, Team away){
        return Objects.equals(home, away);
    }
}
