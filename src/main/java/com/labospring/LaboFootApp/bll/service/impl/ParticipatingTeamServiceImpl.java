package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.events.TeamForfeitedEvent;
import com.labospring.LaboFootApp.bll.exceptions.*;
import com.labospring.LaboFootApp.bll.service.*;
import com.labospring.LaboFootApp.bll.service.models.ParticipatingTeamBusiness;
import com.labospring.LaboFootApp.dal.repositories.ParticipatingTeamRepository;
import com.labospring.LaboFootApp.dl.entities.*;
import com.labospring.LaboFootApp.dl.enums.SubscriptionStatus;
import com.labospring.LaboFootApp.bll.validators.DispatchingTeamsValidator;
import com.labospring.LaboFootApp.bll.validators.ParticipatingTeamStatusValidator;
import com.labospring.LaboFootApp.dl.enums.TournamentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipatingTeamServiceImpl implements ParticipatingTeamService {

    private final RankingService rankingService;
    private final ParticipatingTeamRepository participatingTeamRepository;
    private final TournamentService tournamentService;
    private final TeamService teamService;
    private final BracketService bracketService;
    private final ApplicationEventPublisher eventPublisher;

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
    @Transactional
    public ParticipatingTeam.ParticipatingTeamId createOne(ParticipatingTeamBusiness entityBusiness) {
        Tournament tournament = tournamentService.getOne(entityBusiness.tournamentId());
        Team team = teamService.getOne(entityBusiness.teamId());

        validateTeamNotAlreadyParticipating(tournament, team);

        ParticipatingTeam participatingTeam = new ParticipatingTeam(tournament, team);
        return participatingTeamRepository.save(participatingTeam).getId();
    }

    private void validateTeamNotAlreadyParticipating(Tournament tournament, Team team) {
        ParticipatingTeam.ParticipatingTeamId ptId = new ParticipatingTeam.ParticipatingTeamId(tournament.getId(), team.getId());
        if (participatingTeamRepository.findById(ptId).isPresent()) {
            throw new AlreadyExistParticipatingTeamException(
                    "The team with id: " + team.getId() + " is already participating in the tournament with id: " + tournament.getId()
            );
        }
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
        Tournament tournament = tournamentService.getOne(participatingTeam.getTournament().getId());
        Team team = teamService.getOne(participatingTeam.getTeam().getId());

        if(participatingTeam != null){
            ParticipatingTeamStatusValidator.validateNewStatus(participatingTeam, newStatus);
            SubscriptionStatus statusBeforeChange = participatingTeam.getSubscriptionStatus();
            if (tournament.getTournamentStatus() == TournamentStatus.STARTED &&
                    (newStatus == SubscriptionStatus.CANCELED ||
                            newStatus == SubscriptionStatus.REJECTED
                    )
            ){
                newStatus = SubscriptionStatus.FORFEITED;
                eventPublisher.publishEvent(new TeamForfeitedEvent(this, team, tournament));

            }
            participatingTeam.setSubscriptionStatus(newStatus);
            participatingTeamRepository.save(participatingTeam);

            if(tournament.getTournamentType().isGroupStage()){
                if (statusBeforeChange == SubscriptionStatus.ACCEPTED &&
                        (tournament.getTournamentStatus() == TournamentStatus.BUILDING ||
                                tournament.getTournamentStatus() == TournamentStatus.PENDING
                        )
                   ){
                    Ranking ranking = rankingService.getByTournamentIdAndTeamId(tournament.getId(), team.getId());
                    if (ranking != null){
                        rankingService.deleteOne(ranking.getId());
                    }
                }
                if (newStatus == SubscriptionStatus.ACCEPTED) {
                    rankingService.createOne(tournament, team);
                }

                if (newStatus == SubscriptionStatus.FORFEITED){
                    Ranking ranking = rankingService.getByTournamentIdAndTeamId(participatingTeam.getTournament().getId(), participatingTeam.getTeam().getId());
                    rankingService.closeRanking(ranking);
                }
            }
        }
    }

    @Override
    public void changeStatusToCanceled(ParticipatingTeam.ParticipatingTeamId id) {
        changeStatus(id, SubscriptionStatus.CANCELED);
    }

    @Transactional
    public void dispatchTeamsToGroups(Long tournamentId) {
        Tournament tournament = tournamentService.getOne(tournamentId);
        if(tournament != null){

            DispatchingTeamsValidator.validateTeamDispatch(tournament); // verifie si les ParticipatingTeam tournois peuvent être réparties dans des groupes sinon lève une exception

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
