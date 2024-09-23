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
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.Referee;
import com.labospring.LaboFootApp.dl.entities.Team;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.enums.MatchStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        Team teamHome = teamService.getOne(entityBusiness.teamHomeId());
        Team teamAway = teamService.getOne(entityBusiness.teamAwayId());
        Tournament tournament = tournamentRepository.findById(entityBusiness.tournamentId()).orElseThrow(()-> new RuntimeException("No ID " + entityBusiness.tournamentId()));
        Referee referee = null;
        if(entityBusiness.refereeId() != null)
             referee = refereeService.getOne(entityBusiness.refereeId());

        return footMatchRepository.save(entityBusiness.toEntity(teamHome, teamAway, tournament, referee)).getId();
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
    public void updateOne(Long id, FootMatchBusiness entityBusiness) {
        FootMatch footMatch = getOne(id);


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

}
