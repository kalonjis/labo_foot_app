package com.labospring.LaboFootApp.dal.repositories;

import com.labospring.LaboFootApp.dl.entities.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RankingRepository extends JpaRepository<Ranking, Long> {

    // Méthode pour récupérer un Ranking à partir d'un tournoi et d'une équipe
    @Query("SELECT r FROM Ranking r WHERE r.tournament.id = :tournamentId AND r.team.id = :teamId")
    Ranking findByTournamentIdAndTeamId(@Param("tournamentId") Long tournamentId, @Param("teamId") Long teamId);

}
