package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.exceptions.DoesntExistsException;
import com.labospring.LaboFootApp.bll.service.*;
import com.labospring.LaboFootApp.bll.service.models.FootMatchEditBusiness;
import com.labospring.LaboFootApp.bll.service.models.FootMatchCreateBusiness;
import com.labospring.LaboFootApp.bll.service.models.ScoreBusiness;
import com.labospring.LaboFootApp.dal.repositories.FootMatchRepository;
import com.labospring.LaboFootApp.dl.entities.*;
import com.labospring.LaboFootApp.dl.enums.MatchStage;
import com.labospring.LaboFootApp.dl.enums.MatchStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FootMatchServiceImpl implements FootMatchService {
    
    private final FootMatchRepository footMatchRepository;
    private final TeamService teamService;
    private final RefereeService refereeService;
    private final TournamentService tournamentService;
    private final ValidMatchService validMatchService;


    @Override
    @Transactional
    public Long addOne(FootMatchEditBusiness entityBusiness) {

        FootMatch footMatch= turnIntoFootMatch(entityBusiness);
        if(!validMatchService.isValid(footMatch))
            throw new RuntimeException("Not valid Match");

        footMatch.setMatchStatus(MatchStatus.SCHEDULED);
        return footMatchRepository.save(footMatch).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public FootMatch getOne(Long id) {
        return footMatchRepository.findById(id).orElseThrow(() -> new DoesntExistsException("No Football Match with ID : " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FootMatch> getAll() {
        return footMatchRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteOne(Long id) {
        FootMatch footMatch = getOne(id);
        footMatchRepository.delete(footMatch);
    }

    @Override
    @Transactional
    public void updateOne(Long id, FootMatchEditBusiness entityBusiness) {
        FootMatch footMatch = getOne(id);
        FootMatch footMatchUpdated= turnIntoFootMatch(entityBusiness);

        footMatch.setMatchStage(footMatchUpdated.getMatchStage());
        footMatch.setMatchDateTime(footMatchUpdated.getMatchDateTime());
        footMatch.setFieldLocation(entityBusiness.getFieldLocation());
        footMatch.setReferee(footMatchUpdated.getReferee());
        footMatch.setTeamHome(footMatchUpdated.getTeamHome());
        footMatch.setTeamAway(footMatchUpdated.getTeamAway());

        if(!validMatchService.isValid(footMatch))
            throw new RuntimeException("Not valid Match");

        footMatchRepository.save(footMatch);
    }

    @Override
    @Transactional
    public void changeStatus(Long id, MatchStatus matchStatus){
        FootMatch footMatch = getOne(id);

        footMatch.setMatchStatus(matchStatus);

        footMatchRepository.save(footMatch);
    }

    @Override
    @Transactional
    public void changeScore(Long id, ScoreBusiness scoreBusiness) {
        FootMatch footMatch = getOne(id);
        footMatch.setScoreTeamAway(scoreBusiness.scoreAway());
        footMatch.setScoreTeamHome(scoreBusiness.scoreHome());

        footMatchRepository.save(footMatch);

    }

    @Override
    @Transactional
    public void changeModerator(Long id, Long moderatorId){
        FootMatch footMatch = getOne(id);

        //footMatch.setUserModerator(userService.getOne(id));

        footMatchRepository.save(footMatch);
    }

    @Override
    public FootMatch buildMatchForBracket(Tournament tournament, MatchStage matchStage) {
        if(tournament == null || matchStage == null)
            throw new RuntimeException("Tournament or MatchStage is needed when building a Match for Bracket");

        FootMatch footMatch = new FootMatch();
        footMatch.setMatchStage(matchStage);
        footMatch.setMatchDateTime(tournament.getStartDate());
        footMatch.setTournament(tournament);
        return footMatch;
    }

    private FootMatch turnIntoFootMatch(FootMatchEditBusiness entityBusiness){
        if(Objects.equals(entityBusiness.getTeamHomeId(), entityBusiness.getTeamAwayId()))
            throw new RuntimeException("Teams can't be the same");
        Team teamHome = teamService.getOne(entityBusiness.getTeamHomeId());
        Team teamAway = teamService.getOne(entityBusiness.getTeamAwayId());

        Tournament tournament = entityBusiness instanceof FootMatchCreateBusiness ?
                tournamentService.getOne(((FootMatchCreateBusiness) entityBusiness).getTournamentId()) : null;

        Referee referee = null;
        if(entityBusiness.getRefereeId() != null)
            referee = refereeService.getOne(entityBusiness.getRefereeId());

        return entityBusiness.toEntity(teamHome, teamAway, tournament, referee);
    }
}
