package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.exceptions.AlreadyExistParticipatingTeamException;
import com.labospring.LaboFootApp.bll.exceptions.DoesntExistsException;
import com.labospring.LaboFootApp.bll.exceptions.IncorrectAcceptedTeamsSizeException;
import com.labospring.LaboFootApp.bll.exceptions.IncorrectSubscriptionStatusException;
import com.labospring.LaboFootApp.bll.service.*;
import com.labospring.LaboFootApp.bll.service.models.ParticipatingTeamBusiness;
import com.labospring.LaboFootApp.dal.repositories.ParticipatingTeamRepository;
import com.labospring.LaboFootApp.dl.entities.*;
import com.labospring.LaboFootApp.dl.enums.SubscriptionStatus;
import com.labospring.LaboFootApp.dl.enums.TournamentType;
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
        validateStatusChange(id, newStatus); // Utilisation de la méthode de validation avant de changer le statut

        ParticipatingTeam pt = getOneById(id);
        SubscriptionStatus statusBeforeChange = pt.getSubscriptionStatus();
        pt.setSubscriptionStatus(newStatus);
        participatingTeamRepository.save(pt);

        Tournament tournament = tournamentService.getOne(pt.getTournament().getId());
        if(tournament.getTournamentType().isGroupStage()){
            // Si le nouveau statut n'est plus "ACCEPTED", supprimer son ranking
            if (statusBeforeChange == SubscriptionStatus.ACCEPTED){
                Ranking ranking = rankingService.getByTournamentIdAndTeamId(id.getTournamentId(), id.getTeamId());
                if (ranking != null){
                    rankingService.deleteOne(ranking.getId());
                }
            }
            // Si le nouveau statut est ACCEPTED, créer un ranking
            if (newStatus == SubscriptionStatus.ACCEPTED) {
                Team team = teamService.getOne(pt.getTeam().getId());
                rankingService.createOne(tournament, team);
            }
        }
    }

    private void validateStatusChange(ParticipatingTeam.ParticipatingTeamId id, SubscriptionStatus newStatus) {
        // Vérifier si le statut est valide
        if (newStatus == null || !SubscriptionStatus.contains(newStatus)) {
            throw new IncorrectSubscriptionStatusException(
                    newStatus + " is not a valid SubscriptionStatus. Please use one of the following: {PENDING, ACCEPTED, REJECTED, CANCELED}", 400
            );
        }

        // Vérifier si le tournoi existe
        Tournament tournament = tournamentService.getOne(id.getTournamentId());

        // Vérifier si l'équipe existe
        Team team = teamService.getOne(id.getTeamId());

        // Vérifier si la `ParticipatingTeam` existe
        ParticipatingTeam participatingTeam = getOneById(id);

        // Vérifier la transition de statut
        SubscriptionStatus currentStatus = participatingTeam.getSubscriptionStatus();
        if (!isStatusTransitionValid(currentStatus, newStatus)) {
            throw new IncorrectSubscriptionStatusException(
                    "Transition from " + currentStatus + " to " + newStatus + " is not allowed.", 400
            );
        }

        // Vérifier le nombre maximum d'équipes acceptées
        if (newStatus == SubscriptionStatus.ACCEPTED) {
            int acceptedTeams = getAllByTournamentAndStatus(id.getTournamentId(), SubscriptionStatus.ACCEPTED).size();
            int nbMaxAcceptedTeams = tournamentService.getOne(id.getTournamentId()).getTournamentType().getNbTeams();
            if (acceptedTeams >= nbMaxAcceptedTeams) {
                throw new IncorrectAcceptedTeamsSizeException("The tournament has already accepted the maximum number of participating teams.");
            }
        }
    }

    private boolean isStatusTransitionValid(SubscriptionStatus currentStatus, SubscriptionStatus newStatus) {
        // Définir les transitions autorisées
        return switch (currentStatus) {
            case PENDING ->
                    newStatus == SubscriptionStatus.ACCEPTED || newStatus == SubscriptionStatus.REJECTED || newStatus == SubscriptionStatus.CANCELED;
            case ACCEPTED -> newStatus == SubscriptionStatus.CANCELED || newStatus == SubscriptionStatus.REJECTED;
            case REJECTED ->
                    newStatus == SubscriptionStatus.PENDING;
            case CANCELED ->
                    newStatus == SubscriptionStatus.PENDING;
        };
    }

    @Override
    public void changeStatusToCanceled(ParticipatingTeam.ParticipatingTeamId id) {
        ParticipatingTeam pt = getOneById(id);
        pt.setSubscriptionStatus(SubscriptionStatus.CANCELED);
        participatingTeamRepository.save(pt);
    }

    // Méthode pour dispatcher les équipes dans les groupes
    @Transactional
    public void dispatchTeamsToGroups(Long tournamentId) {
        // Récupérer le tournoi en question
        Tournament tournament = tournamentService.getOne(tournamentId);
        List<Ranking> rankingList = tournament.getRankingList();

        // Récupérer le type de tournoi
        TournamentType tournamentType = tournament.getTournamentType();

        // Vérifier s'il y a une phase de groupes
        if (!tournamentType.isGroupStage()) {
            throw new RuntimeException("The tournament does not have a group stage.");
        }

         //Vérifier si le nombre d'équipes correspond au nombre d'équipes attendu
        if (rankingList.size() != tournamentType.getNbTeams()) {
            throw new RuntimeException("The number of teams does not match the required number for this tournament.");
        }

        // Obtenir le nombre de groupes
        int nbGroups = tournamentType.getNbGroups();

        // Créer une liste d'index de groupes
        List<Integer> groupIndexes = IntStream.rangeClosed(1, nbGroups).boxed().toList();

        // Mélanger la liste de classements aléatoirement pour la répartition
        Collections.shuffle(rankingList);

        // Répartir les équipes dans les groupes
        for (int i = 0; i < rankingList.size(); i++) {
            Ranking ranking = rankingList.get(i);
            int numGroup = groupIndexes.get(i % nbGroups); // Assigner le groupe en mode cyclique
            rankingService.updateNumGroup(ranking, numGroup);
//            rankingService.updateOne(ranking.getId(), new RankingBusiness(ranking.getTournament().getId(), groupNumber)); // Mise à jour avec le nouveau groupe
        }

        // Sauvegarder tous les changements
        rankingList.forEach(ranking -> rankingService.updateOne(ranking.getId(), null));
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
