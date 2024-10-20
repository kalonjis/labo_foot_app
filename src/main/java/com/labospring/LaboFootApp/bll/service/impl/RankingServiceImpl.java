package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.exceptions.BadStatusRankingException;
import com.labospring.LaboFootApp.bll.exceptions.DoesntExistsException;
import com.labospring.LaboFootApp.bll.exceptions.IncorrectRankingListSize;
import com.labospring.LaboFootApp.bll.service.RankingService;
import com.labospring.LaboFootApp.bll.service.models.RankingBusiness;
import com.labospring.LaboFootApp.bll.service.models.RankingEditBusiness;
import com.labospring.LaboFootApp.dal.repositories.RankingRepository;
import com.labospring.LaboFootApp.dl.entities.Ranking;
import com.labospring.LaboFootApp.dl.entities.Team;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.labospring.LaboFootApp.dl.consts.RankingPoint.*;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

    private final RankingRepository rankingRepository;


    @Transactional
    @Override
    public Long createOne(Tournament tournament, Team team) {
        if (tournament == null || team == null) {
            throw new IllegalArgumentException("Tournament or Team cannot be null");
        }
        validateRankingListSize(tournament);
        Ranking r = new Ranking(tournament, team);
        return rankingRepository.save(r).getId();
    }

    @Override
    public Ranking getOne(Long id) {
        return rankingRepository.findById(id).orElseThrow(() -> new DoesntExistsException("No Ranking with ID " + id));
    }

    @Override
    public Ranking getByTournamentIdAndTeamId(Long tournamentId, Long teamId) {
        return rankingRepository.findByTournamentIdAndTeamId(tournamentId, teamId).orElseThrow(()-> new DoesntExistsException("There is no ranking for the team with ID : " + teamId + " related to the tournament with id : " + tournamentId ));
    }

    @Override
    public List<Ranking> getAllByTournamentId(Long tournamentId){
        return rankingRepository.findByTournamentId(tournamentId);
    }

    @Override
    public List<Ranking> getAllByTournamentIdAndNumGroup(Long tournamentId, int numGroup){
        return rankingRepository.findByTournamentIdAndNumGroup(tournamentId, numGroup);
    }

    @Override
    public List<Ranking> getAll() {
        return rankingRepository.findAll();
    }

    @Override
    public void deleteOne(Long id) {
        Ranking ranking = getOne(id);
        rankingRepository.delete(ranking);
    }

    @Override
    public void openRanking(Ranking ranking){
        if (ranking.isOpen()){
            throw new BadStatusRankingException("Impossible to open the ranking with id : " + ranking.getId() + " because it's already open !");
        }
        ranking.setOpen(true);
        rankingRepository.save(ranking);
    }

    @Override
    public void closeRanking(Ranking ranking){
        if (!ranking.isOpen()){
            throw new BadStatusRankingException("Impossible to close the ranking with id : " + ranking.getId() + " because it's already closed !");
        }
        ranking.setOpen(false);
        rankingRepository.save(ranking);
    }

    @Override
    public void updateStartingMatch(Ranking rankingTeamHome, Ranking rankingTeamAway){
        if (!rankingTeamHome.isOpen()){
            throw new BadStatusRankingException("Impossible to update the ranking with id : " + rankingTeamHome.getId() + " because it's closed !");
        }
        if (!rankingTeamAway.isOpen()){
            throw new BadStatusRankingException("Impossible to update the ranking with id : " + rankingTeamAway.getId() + " because it's closed !");
        }
        rankingTeamHome.setNbMatchPlayed(rankingTeamHome.getNbMatchPlayed() + 1);
        rankingTeamAway.setNbMatchPlayed(rankingTeamAway.getNbMatchPlayed() + 1);
        rankingTeamHome.setNbDraws(rankingTeamHome.getNbDraws() + 1);
        rankingTeamAway.setNbDraws(rankingTeamAway.getNbDraws() + 1);
        calculTotalPoints(rankingTeamHome);
        calculTotalPoints(rankingTeamAway);
        rankingRepository.save(rankingTeamHome);
        rankingRepository.save(rankingTeamAway);
    }

    @Override
    public void updateGettingWinner(Ranking winningRanking, Ranking losingRanking){
        if (!winningRanking.isOpen()){
            throw new BadStatusRankingException("Impossible to update the ranking with id : " + winningRanking.getId() + " because it's closed !");
        }
        if (!losingRanking.isOpen()){
            throw new BadStatusRankingException("Impossible to update the ranking with id : " + losingRanking.getId() + " because it's closed !");
        }
        winningRanking.setNbDraws(winningRanking.getNbDraws() - 1);
        losingRanking.setNbDraws(losingRanking.getNbDraws() - 1);
        winningRanking.setNbWins(winningRanking.getNbWins() + 1);
        losingRanking.setNbLosses(losingRanking.getNbLosses() + 1);
        calculTotalPoints(winningRanking);
        calculTotalPoints(losingRanking);
        rankingRepository.save(winningRanking);
        rankingRepository.save(losingRanking);
    }

    @Override
    public void updateGettingWinnerFromLoser(Ranking winningRanking, Ranking losingRanking){
        if (!winningRanking.isOpen()){
            throw new BadStatusRankingException("Impossible to update the ranking with id : " + winningRanking.getId() + " because it's closed !");
        }
        if (!losingRanking.isOpen()){
            throw new BadStatusRankingException("Impossible to update the ranking with id : " + losingRanking.getId() + " because it's closed !");
        }
        winningRanking.setNbWins(winningRanking.getNbWins() + 1);
        winningRanking.setNbLosses(winningRanking.getNbLosses() - 1);
        losingRanking.setNbLosses(losingRanking.getNbLosses() + 1);
        losingRanking.setNbWins(losingRanking.getNbWins() - 1);
        calculTotalPoints(winningRanking);
        calculTotalPoints(losingRanking);
        rankingRepository.save(winningRanking);
        rankingRepository.save(losingRanking);

    }

    @Override
    public void updateGettingDrawer(Ranking fromwinnerRanking, Ranking fromLoserRanking){
        if (!fromwinnerRanking.isOpen()){
            throw new BadStatusRankingException("Impossible to update the ranking with id : " + fromwinnerRanking.getId() + " because it's closed !");
        }
        if (!fromLoserRanking.isOpen()){
            throw new BadStatusRankingException("Impossible to update the ranking with id : " + fromLoserRanking.getId() + " because it's closed !");
        }
        fromwinnerRanking.setNbWins(fromwinnerRanking.getNbWins() - 1);
        fromwinnerRanking.setNbDraws(fromwinnerRanking.getNbDraws() + 1);
        fromLoserRanking.setNbLosses(fromLoserRanking.getNbLosses() - 1);
        fromLoserRanking.setNbDraws(fromLoserRanking.getNbDraws() + 1);
        calculTotalPoints(fromwinnerRanking);
        calculTotalPoints(fromLoserRanking);
        rankingRepository.save(fromwinnerRanking);
        rankingRepository.save(fromLoserRanking);
    }

    @Override
    public void updateGoalsFor(Ranking ranking, int goals){
        if (!ranking.isOpen()){
            throw new BadStatusRankingException("Impossible to update the ranking with id : " + ranking.getId() + " because it's closed !");
        }
        ranking.setGoalsFor(ranking.getGoalsFor() + goals);
        calculGoalsDiff(ranking);
        rankingRepository.save(ranking);
    }

    @Override
    public void updateGoalsAgainst(Ranking ranking, int goals){
        if (!ranking.isOpen()){
            throw new BadStatusRankingException("Impossible to update the ranking with id : " + ranking.getId() + " because it's closed !");
        }
        ranking.setGoalsAgainst(ranking.getGoalsAgainst() + goals);
        calculGoalsDiff(ranking);
        rankingRepository.save(ranking);
    }

    public void updatePosition(Ranking ranking) {
        // Récupérer tous les classements du même tournoi et du même groupe
        List<Ranking> rankings = getAllByTournamentIdAndNumGroup(ranking.getTournament().getId(), ranking.getNumGroup());

        // Trier les classements par `totalPoints` (du plus élevé au plus faible) puis par `goalsDiff` en cas d'égalité
        rankings.sort((r1, r2) -> {
            if (r2.getTotalPoints() != r1.getTotalPoints()) {
                return Integer.compare(r2.getTotalPoints(), r1.getTotalPoints()); // Tri décroissant par `totalPoints`
            } else if (r2.getGoalsDiff() != r1.getGoalsDiff()) {
                return Integer.compare(r2.getGoalsDiff(), r1.getGoalsDiff()); // En cas d'égalité, tri décroissant par `goalsDiff`
            } else if (r2.getGoalsFor() != r1.getGoalsFor()) {
                return Integer.compare(r2.getGoalsFor(), r1.getGoalsFor()); // En cas d'égalité, tri décroissant par `goalsFor`
            } else {
                return Integer.compare(r1.getNbMatchPlayed(), r2.getNbMatchPlayed()); // En cas d'égalité, tri croissant par nbMatchPlayed
            }
        });

        // Mettre à jour la position des équipes après le tri
        int currentPosition = 1;  // Position actuelle
        int previousRank = 1;     // Position précédente
        Ranking previousRanking = null;  // Classement précédent pour comparaison

        for (int i = 0; i < rankings.size(); i++) {
            Ranking currentRanking = rankings.get(i);
            if (previousRanking != null &&
                    currentRanking.getTotalPoints() == previousRanking.getTotalPoints() &&
                    currentRanking.getGoalsDiff() == previousRanking.getGoalsDiff() &&
                    currentRanking.getGoalsFor() == previousRanking.getGoalsFor() &&
                    currentRanking.getNbMatchPlayed() == previousRanking.getNbMatchPlayed()) {
                // En cas d'égalité stricte, attribuer la même position que l'équipe précédente
                currentRanking.setRankingPosition(previousRank);
            } else {
                // Sinon, attribuer la position courante
                currentRanking.setRankingPosition(currentPosition);
                previousRank = currentPosition; // Mettre à jour la position précédente
            }
            previousRanking = currentRanking; // Mettre à jour le classement précédent
            currentPosition++; // Avancer la position actuelle pour la prochaine équipe

            rankingRepository.save(currentRanking);
        }
    }


    @Override
    public void update(Long id, RankingEditBusiness entityBusiness) {
        Ranking ranking = getOne(id);  // Récupérer le classement existant

        // Mettre à jour les propriétés seulement si elles ne sont pas nulles
        if (entityBusiness.nbWins() != null) {
            ranking.setNbWins(entityBusiness.nbWins());
        }

        if (entityBusiness.nbLosses() != null) {
            ranking.setNbLosses(entityBusiness.nbLosses());
        }

        if (entityBusiness.nbDraws() != null) {
            ranking.setNbDraws(entityBusiness.nbDraws());
        }

        if (entityBusiness.goalsFor() != null) {
            ranking.setGoalsFor(entityBusiness.goalsFor());
        }

        if (entityBusiness.goalsAgainst() != null) {
            ranking.setGoalsAgainst(entityBusiness.goalsAgainst());
        }

        // Calculer les différences de buts et points totaux après les mises à jour
        calculGoalsDiff(ranking);
        calculTotalPoints(ranking);

        rankingRepository.save(ranking);
    }

    private void calculGoalsDiff(Ranking ranking) {
        ranking.setGoalsDiff(
                ranking.getGoalsFor() - ranking.getGoalsAgainst()
        );
    }

    private void calculTotalPoints(Ranking ranking) {
        ranking.setTotalPoints(
                (POINT_BY_WINS * ranking.getNbWins()) +
                        (POINT_BY_DRAW * ranking.getNbDraws()) +
                        (POINT_BY_LOSES * ranking.getNbLosses())
        );
    }

    @Override
    public void updateNumGroup(Ranking ranking, int numGroup) {
        if (ranking.isOpen()){
            throw new BadStatusRankingException("Impossible to update the num_group for ranking with id : " + ranking.getId() + " because it's open !");
        }
        ranking.setNumGroup(numGroup);
        rankingRepository.save(ranking);
    }


    private void validateRankingListSize(Tournament tournament) {
        int nbMaxRankings = tournament.getTournamentType().getNbTeams();
        if (tournament.getRankingList().size() >= nbMaxRankings) {
            throw new IncorrectRankingListSize("Impossible to create more ranking than teams allowed for this tournament");
        }
    }



    // region Methode non implementées de BaseService
    @Override
    public void updateOne(Long id, RankingBusiness entityBusiness) {
    }

    @Override
    public Long addOne(RankingBusiness entityBusiness) {
        return 0L;
    }
    // endregion
}
