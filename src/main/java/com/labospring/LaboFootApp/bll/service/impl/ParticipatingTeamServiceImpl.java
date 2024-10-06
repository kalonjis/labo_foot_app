package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.exceptions.*;
import com.labospring.LaboFootApp.bll.service.*;
import com.labospring.LaboFootApp.bll.service.models.ParticipatingTeamBusiness;
import com.labospring.LaboFootApp.dal.repositories.ParticipatingTeamRepository;
import com.labospring.LaboFootApp.dl.entities.*;
import com.labospring.LaboFootApp.dl.enums.SubscriptionStatus;
import com.labospring.LaboFootApp.il.validators.DispatchingTeamsValidator;
import com.labospring.LaboFootApp.il.validators.ParticipatingTeamStatusValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ParticipatingTeamServiceImpl implements ParticipatingTeamService {

    private final RankingService rankingService;
    private final ParticipatingTeamRepository participatingTeamRepository;
    private final TournamentService tournamentService;
    private final TeamService teamService;
    private final BracketService bracketService;

    @Override
    public List<ParticipatingTeam> getAll() {
        return participatingTeamRepository.findAll();
    }

    @Override
    public List<ParticipatingTeam> getAllTeamsByTournament(Long tournamentId){
        if(! tournamentService.tournamentExists(tournamentId)){
            throw new DoesntExistsException("The tournament with id : "+ tournamentId + " doesn't exists", 404);
        }else{
            return participatingTeamRepository.findAllTeamsByTournamentId(tournamentId);
        }
    }

    @Override
    public List<ParticipatingTeam> getAllByTournamentAndStatus(Long tournamentId, SubscriptionStatus status){
        if(! tournamentService.tournamentExists(tournamentId)) {
            throw new DoesntExistsException("The tournament with id : " + tournamentId + " doesn't exists", 404);
        }if(! SubscriptionStatus.contains(status)){
            throw  new IncorrectSubscriptionStatusException(status + "is not a valid SubscriptionStatus. Please use one of the followings: {PENDING, ACCEPTED, REJECTED, CANCELED}", 400);
        }
            return participatingTeamRepository.findAllByTournamentAndStatus(tournamentId, status);

    }

    @Override
    public ParticipatingTeam.ParticipatingTeamId createOne(ParticipatingTeamBusiness entityBusiness) {
        Tournament tournament = tournamentService.getOne(entityBusiness.tournamentId());
        Team team = teamService.getOne(entityBusiness.teamId());
        ParticipatingTeam.ParticipatingTeamId ptId = new ParticipatingTeam.ParticipatingTeamId(tournament.getId(), team.getId());
        if(participatingTeamRepository.findById(ptId).isPresent()){
            throw new AlreadyExistParticipatingTeamException("the team with id : " + team.getId() + " is already participating in the tournament with id : " + tournament.getId());
        }
        return participatingTeamRepository.save(new ParticipatingTeam(tournament, team)).getId();
    }

    @Override
    public ParticipatingTeam getOneById(ParticipatingTeam.ParticipatingTeamId id) {
        return participatingTeamRepository.findById(id).orElseThrow(() -> new DoesntExistsException(
                "the team with id : " + id.getTeamId() + " isn't participating to the tournament with id: " + id.getTournamentId())
        );
    }

    @Override
    public void deleteById(ParticipatingTeam.ParticipatingTeamId id){
        ParticipatingTeam pt = getOneById(id);
        participatingTeamRepository.delete(pt);
    }

    @Override
    @Transactional
    public void changeStatus(ParticipatingTeam.ParticipatingTeamId id, SubscriptionStatus newStatus) {

        ParticipatingTeam participatingTeam = getOneById(id);
        if(participatingTeam != null){
            ParticipatingTeamStatusValidator participatingTeamStatusValidator = new ParticipatingTeamStatusValidator(participatingTeam, newStatus);
            if(!participatingTeamStatusValidator.isValidStatusChange()){
                throw participatingTeamStatusValidator.getParticipatingTeamSatusChangeException();
            }
            SubscriptionStatus statusBeforeChange = participatingTeam.getSubscriptionStatus();
            participatingTeam.setSubscriptionStatus(newStatus);
            participatingTeamRepository.save(participatingTeam);

            if(participatingTeam.getTournament().getTournamentType().isGroupStage()){
                if (statusBeforeChange == SubscriptionStatus.ACCEPTED){
                    Ranking ranking = rankingService.getByTournamentIdAndTeamId(id.getTournamentId(), id.getTeamId());
                    if (ranking != null){
                        rankingService.deleteOne(ranking.getId());
                    }
                }
                if (newStatus == SubscriptionStatus.ACCEPTED) {
                    Team team = teamService.getOne(participatingTeam.getTeam().getId());
                    rankingService.createOne(participatingTeam.getTournament(), team);
                }
            }
        }
    }

    @Override
    public void changeStatusToCanceled(ParticipatingTeam.ParticipatingTeamId id) {
        ParticipatingTeam pt = getOneById(id);
        pt.setSubscriptionStatus(SubscriptionStatus.CANCELED);
        participatingTeamRepository.save(pt);
    }

    @Transactional
    public void dispatchTeamsToGroups(Long tournamentId) {
        Tournament tournament = tournamentService.getOne(tournamentId);
        if(tournament != null){
            DispatchingTeamsValidator dispatchingTeamsValidator = new DispatchingTeamsValidator(tournament);
            if(!dispatchingTeamsValidator.isDispatchable()){
                throw dispatchingTeamsValidator.getTournamentNotdispatchableException();
            }
            List<Ranking> rankingList = tournament.getRankingList();
            int nbGroups = tournament.getTournamentType().getNbGroups();
            List<Integer> groupIndexes = tournament.getTournamentType().getGroups();
            Collections.shuffle(rankingList);

            for (int i = 0; i < rankingList.size(); i++) {
                Ranking ranking = rankingList.get(i);
                int numGroup = groupIndexes.get(i % nbGroups); // Assigner le groupe en mode cyclique
                rankingService.updateNumGroup(ranking, numGroup);
            }
            rankingList.forEach(ranking -> rankingService.updateOne(ranking.getId(), null));
        }
    }

    @Transactional
    @Override
    public void dispatchTeamsToBrackets(Long tournamentId){
        var acceptedTeams =  getAllByTournamentAndStatus(tournamentId, SubscriptionStatus.ACCEPTED)
                .stream()
                .map(ParticipatingTeam::getTeam)
                .toList();
        bracketService.createBracketForTournament(tournamentId, acceptedTeams);

    }

}
