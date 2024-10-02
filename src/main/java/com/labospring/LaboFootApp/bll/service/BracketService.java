package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.dl.entities.Bracket;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.Team;

import java.util.List;

public interface BracketService {
    List<Bracket> getListByTournament(Long tournamentId);

    Integer getBracketPosition(FootMatch footMatch);

    void createBracketForTournament(Long tournamentId, List<Team> teams);

    boolean haveBracketByTournament(Long tournamentId);
}
