package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.service.FootMatchService;
import com.labospring.LaboFootApp.bll.service.RefereeService;
import com.labospring.LaboFootApp.bll.service.TeamService;
import com.labospring.LaboFootApp.bll.service.models.FootMatchBusiness;
import com.labospring.LaboFootApp.bll.service.models.ScoreBusiness;
import com.labospring.LaboFootApp.dal.repositories.FootMatchRepository;
import com.labospring.LaboFootApp.dal.repositories.RefereeRepository;
import com.labospring.LaboFootApp.dal.repositories.TeamRepository;
import com.labospring.LaboFootApp.dal.repositories.TournamentRepository;
import com.labospring.LaboFootApp.dl.entities.*;
import com.labospring.LaboFootApp.dl.enums.MatchStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FootMatchServiceImpl implements FootMatchService {
    private final FootMatchRepository footMatchRepository;
    private final TeamService teamService;
    private final RefereeService refereeService;
    private final TournamentRepository tournamentRepository; // TODO A CHANGER


    @Override
    public Long addOne(FootMatchBusiness entityBusiness) {

        return footMatchRepository.save(turnIntoFootMatch(entityBusiness)).getId();
    }

    @Override
    public FootMatch getOne(Long id) {
        return footMatchRepository.findById(id).orElseThrow(() -> new RuntimeException("No Football Match with ID : " + id));
    }

    @Override
    public List<FootMatch> getAll() {
        return footMatchRepository.findAll();
    }

    @Override
    public void deleteOne(Long id) {
        FootMatch footMatch = getOne(id);
        footMatchRepository.delete(footMatch);
    }

    @Override
    @Transactional
    public void updateOne(Long id, FootMatchBusiness entityBusiness) {
        FootMatch footMatch = getOne(id);
        FootMatch footMatchUpdated= turnIntoFootMatch(entityBusiness);

        footMatch.setMatchStage(footMatchUpdated.getMatchStage());
        footMatch.setMatchDateTime(footMatchUpdated.getMatchDateTime());
        footMatch.setFieldLocation(entityBusiness.fieldLocation());
        footMatch.setReferee(footMatchUpdated.getReferee());
        footMatch.setTournament(footMatchUpdated.getTournament());
        footMatch.setTeamHome(footMatchUpdated.getTeamHome());
        footMatch.setTeamAway(footMatchUpdated.getTeamAway());

        footMatchRepository.save(footMatch);

    }

    @Override
    public void changeStatus(Long id, MatchStatus matchStatus){
        FootMatch footMatch = getOne(id);

        footMatch.setMatchStatus(matchStatus);

        footMatchRepository.save(footMatch);
    }

    @Override
    public void changeScore(Long id, ScoreBusiness scoreBusiness) {
        FootMatch footMatch = getOne(id);
        footMatch.setScoreTeamAway(scoreBusiness.scoreAway());
        footMatch.setScoreTeamHome(scoreBusiness.scoreHome());

        footMatchRepository.save(footMatch);

    }

    @Override
    public void changeModerator(Long id, Long moderatorId){
        FootMatch footMatch = getOne(id);

        //footMatch.setUserModerator(userService.getOne(id));

        footMatchRepository.save(footMatch);
    }

    private FootMatch turnIntoFootMatch(FootMatchBusiness entityBusiness){
        Team teamHome = teamService.getOne(entityBusiness.teamHomeId());
        Team teamAway = teamService.getOne(entityBusiness.teamAwayId());
        Tournament tournament = tournamentRepository.findById(entityBusiness.tournamentId()).orElseThrow(()-> new RuntimeException("No ID " + entityBusiness.tournamentId()));
        Referee referee = null;
        if(entityBusiness.refereeId() != null)
            referee = refereeService.getOne(entityBusiness.refereeId());

        return entityBusiness.toEntity(teamHome, teamAway, tournament, referee);
    }
}
