package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.service.BracketService;
import com.labospring.LaboFootApp.bll.service.TournamentService;
import com.labospring.LaboFootApp.dal.repositories.BracketRepository;
import com.labospring.LaboFootApp.dl.entities.Bracket;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class BracketServiceImpl implements BracketService {
    private final BracketRepository bracketRepository;
    private final TournamentService tournamentService;

    @Override
    public List<Bracket> getListByTournament(Long tournamentId) {
        Tournament tournament = tournamentService.getOne(tournamentId);
        return bracketRepository.findAllByTournament(tournament);
    }
}
