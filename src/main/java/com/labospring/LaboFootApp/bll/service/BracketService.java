package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.dl.entities.Bracket;
import com.labospring.LaboFootApp.dl.entities.Tournament;

import java.util.List;

public interface BracketService {
    List<Bracket> getListByTournament(Long tournamentId);
}
