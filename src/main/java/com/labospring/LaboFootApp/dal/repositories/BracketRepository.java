package com.labospring.LaboFootApp.dal.repositories;

import com.labospring.LaboFootApp.dl.entities.Bracket;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BracketRepository extends JpaRepository<Bracket, Long> {
    @Query("select b from Bracket b where b.tournament = :tournament")
    List<Bracket> findAllByTournament(Tournament tournament);
}
