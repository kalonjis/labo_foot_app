package com.labospring.LaboFootApp.dal.repositories;

import com.labospring.LaboFootApp.dl.entities.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RankingRepository extends JpaRepository<Ranking, Long> {

    // Méthode pour récupérer un Ranking à partir d'un tournoi et d'une équipe
    @Query("SELECT r FROM Ranking r WHERE r.tournament.id = :tournamentId AND r.team.id = :teamId")
    Optional<Ranking> findByTournamentIdAndTeamId(@Param("tournamentId") Long tournamentId, @Param("teamId") Long teamId);

    @Query("SELECT r FROM Ranking r where r.tournament.id = :tournamentId AND r.numGroup = :numGroup")
    List<Ranking> findByTournamentIdAndNumGroup(@Param("tournamentId") Long tournamentId, @Param("numGroup") int numGroup);

}
