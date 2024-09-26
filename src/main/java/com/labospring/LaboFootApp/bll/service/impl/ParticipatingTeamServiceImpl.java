package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.service.ParticipatingTeamService;
import com.labospring.LaboFootApp.bll.service.RankingService;
import com.labospring.LaboFootApp.bll.service.TeamService;
import com.labospring.LaboFootApp.bll.service.TournamentService;
import com.labospring.LaboFootApp.bll.service.models.ParticipatingTeamBusiness;
import com.labospring.LaboFootApp.dal.repositories.ParticipatingTeamRepository;
import com.labospring.LaboFootApp.dl.entities.ParticipatingTeam;
import com.labospring.LaboFootApp.dl.entities.Team;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.enums.SubscriptionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipatingTeamServiceImpl implements ParticipatingTeamService {

    private final RankingService rankingService;
    private final ParticipatingTeamRepository participatingTeamRepository;
    private final TournamentService tournamentService;
    private final TeamService teamService;


    @Override
    public List<ParticipatingTeam> getAll() {
        return participatingTeamRepository.findAll();
    }

    @Override
    public ParticipatingTeam.ParticipatingTeamId createOne(ParticipatingTeamBusiness entityBusiness) {
        Tournament tournament = tournamentService.getOne(entityBusiness.tournamentId());
        Team team = teamService.getOne(entityBusiness.teamId());
        return new ParticipatingTeam(tournament, team).getId();
    }
    @Override
    public ParticipatingTeam getOneById(ParticipatingTeam.ParticipatingTeamId id) {
        return participatingTeamRepository.findById(id).orElseThrow(() -> new RuntimeException(
                "the team with id : " + id.getTeamId() + " isn't participating to the tournament with id: " + id.getTournamentId())
        );
    }

    @Override
    public void deleteById(ParticipatingTeam.ParticipatingTeamId id){
        ParticipatingTeam pt = getOneById(id);
        participatingTeamRepository.delete(pt);
    }

    @Override
    public void changeStatus(ParticipatingTeam.ParticipatingTeamId id, SubscriptionStatus status) {
        ParticipatingTeam pt = getOneById(id);
        pt.setSubscriptionStatus(status);
        if(status == SubscriptionStatus.ACCEPTED){
            Tournament tournament = tournamentService.getOne(pt.getTournament().getId());
            Team team = teamService.getOne(pt.getTeam().getId());
            rankingService.createOne(tournament, team);
        }
        participatingTeamRepository.save(pt);
    }

    @Override
    public void changeStatusToCanceled(ParticipatingTeam.ParticipatingTeamId id) {
        ParticipatingTeam pt = getOneById(id);
        pt.setSubscriptionStatus(SubscriptionStatus.CANCELED);
        participatingTeamRepository.save(pt);
    }


}
