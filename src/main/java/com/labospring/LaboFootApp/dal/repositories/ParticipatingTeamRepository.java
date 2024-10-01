package com.labospring.LaboFootApp.dal.repositories;

import com.labospring.LaboFootApp.dl.entities.ParticipatingTeam;
import com.labospring.LaboFootApp.dl.entities.Team;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipatingTeamRepository extends JpaRepository<ParticipatingTeam, ParticipatingTeam.ParticipatingTeamId> {
    @Query("select count(p) > 0 from ParticipatingTeam p where p.tournament = :tournament AND p.team = :team AND p.subscriptionStatus = :status")
    boolean existByTeamAndTournamentAndStatus(@Param("team")Team team, @Param("tournament")Tournament tournament, @Param("status") SubscriptionStatus status);

    @Query("SELECT pt FROM ParticipatingTeam pt WHERE pt.tournament.id = :tournamentId")
    List<ParticipatingTeam> findAllTeamsByTournamentId(@Param("tournamentId") Long tournamentId);

    @Query("SELECT pt FROM ParticipatingTeam pt where pt.tournament.id = :tournamentId AND pt.subscriptionStatus = :status")
    List<ParticipatingTeam> findAllByTournamentAndStatus(@Param("tournamentId") Long tournamentId, @Param("status") SubscriptionStatus status);


}
