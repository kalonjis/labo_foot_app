package com.labospring.LaboFootApp.dal.repositories;

import com.labospring.LaboFootApp.dl.entities.Bracket;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BracketRepository extends JpaRepository<Bracket, Long> {
    @Query("select b from Bracket b where b.tournament = :tournament")
    List<Bracket> findAllByTournament(Tournament tournament);

    @Query("SELECT b.positionBracket FROM Bracket b WHERE b.match = :footMatch")
    Integer findPositionByFootMatch(@Param("footMatch") FootMatch footMatch);

    @Query("select count(b) > 0 from Bracket b where b.tournament.id = :tournamentId")
    boolean existsByTournamentId(Long tournamentId);
}
