package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.exceptions.DoesntExistsException;
import com.labospring.LaboFootApp.bll.service.RankingService;
import com.labospring.LaboFootApp.bll.service.models.RankingBusiness;
import com.labospring.LaboFootApp.bll.service.models.RankingEditBusiness;
import com.labospring.LaboFootApp.dal.repositories.RankingRepository;
import com.labospring.LaboFootApp.dl.entities.Ranking;
import com.labospring.LaboFootApp.dl.entities.Team;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.labospring.LaboFootApp.dl.consts.RankingPoint.*;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

    private final RankingRepository rankingRepository;
    private final TournamentServiceImpl tournamentService;

    @Override
    public Long addOne(RankingBusiness entityBusiness) {
        Tournament t = tournamentService.getOne(entityBusiness.tournament_id());
        if (t.getTournamentType().getGroups() == null) {
            throw new RuntimeException("Impossible to create ranking for tournament with id " + entityBusiness.tournament_id() + " because it hasn't any group...");
        }
        Ranking r = new Ranking(t, entityBusiness.numGroup());
        return rankingRepository.save(r).getId();
    }


    @Override
    public Long createOne(Tournament tournament, Team team) {
        Ranking r = new Ranking(tournament, team);
        return rankingRepository.save(r).getId();
    }

    @Override
    public Ranking getOne(Long id) {
        return rankingRepository.findById(id).orElseThrow(() -> new DoesntExistsException("No Ranking with ID " + id));
    }

    @Override
    public Ranking getByTournamentIdAndTeamId(Long tournamentId, Long TeamId) {
        return rankingRepository.findByTournamentIdAndTeamId(tournamentId, TeamId);
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
    public void updateWinnerRanking(Ranking ranking){
        ranking.setNbWins(ranking.getNbWins() + 1);
        calculTotalPoints(ranking);
        rankingRepository.save(ranking);
    }

    @Override
    public void updateLooserRanking(Ranking ranking){
        ranking.setNbLosses(ranking.getNbLosses() + 1);
        calculTotalPoints(ranking);
        rankingRepository.save(ranking);
    }

    @Override
    public void updateDrawerRanking(Ranking ranking){
        ranking.setNbDraws(ranking.getNbDraws() + 1);
        calculTotalPoints(ranking);
        rankingRepository.save(ranking);
    }

    @Override
    public void updateNbMatchPlayed(Ranking ranking){
        ranking.setNbMatchPlayed(ranking.getNbMatchPlayed() + 1);
        rankingRepository.save(ranking);
    }

    @Override
    public void updateGoalsFor(Ranking ranking, int goals){
        ranking.setGoalsFor(ranking.getGoalsFor() + goals);
        calculGoalsDiff(ranking);
        rankingRepository.save(ranking);
    }

    @Override
    public void updateGoalsAgainst(Ranking ranking, int goals){
        ranking.setGoalsAgainst(ranking.getGoalsAgainst() + goals);
        calculGoalsDiff(ranking);
        rankingRepository.save(ranking);
    }

    @Override
    public void updatePosition(Ranking ranking) {
        // Récupérer tous les classements du même tournoi et du même groupe
        List<Ranking> rankings = getAllByTournamentIdAndNumGroup(ranking.getTournament().getId(), ranking.getNumGroup());

        // Trier les classements par `totalPoints` (du plus élevé au plus faible) puis par `goalsDiff` en cas d'égalité
        rankings.sort((r1, r2) -> {
            if (r2.getTotalPoints() != r1.getTotalPoints()) {
                return Integer.compare(r2.getTotalPoints(), r1.getTotalPoints()); // Tri décroissant par `totalPoints`
            } else if (r2.getGoalsDiff()!= r1.getGoalsDiff()){
                return Integer.compare(r2.getGoalsDiff(), r1.getGoalsDiff()); // En cas d'égalité, tri décroissant par `goalsDiff`
            } else if(r2.getGoalsFor() != r1.getGoalsFor()){
                return Integer.compare(r2.getGoalsFor(), r1.getGoalsFor()); // En cas d'égalité, tri décroissant par `goalsFor
            } else {
                return Integer.compare(r1.getNbMatchPlayed(), r2.getNbMatchPlayed()); // En cas d'égalité, tri croissant par nbMatchPlayed
            }
        });

        // Mettre à jour la position des équipes après le tri
        for (int i = 0; i < rankings.size(); i++) {
            Ranking r = rankings.get(i);
            r.setRankingPosition(i + 1); // La position est `i + 1` car la liste est indexée à partir de 0
            rankingRepository.save(r);   // Sauvegarder chaque changement de position
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

        rankingRepository.save(ranking);  // Sauvegarder les changements
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
        ranking.setNumGroup(numGroup);
        rankingRepository.save(ranking);
    }

    @Override
    public void updateOne(Long id, RankingBusiness entityBusiness) {
    }
}
