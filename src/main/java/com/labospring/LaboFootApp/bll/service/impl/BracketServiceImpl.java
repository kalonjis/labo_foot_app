package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.exceptions.BadStatusTournamentException;
import com.labospring.LaboFootApp.bll.service.BracketGeneratorService;
import com.labospring.LaboFootApp.bll.service.BracketService;
import com.labospring.LaboFootApp.bll.service.FootMatchBracketGeneratorService;
import com.labospring.LaboFootApp.bll.service.TournamentService;
import com.labospring.LaboFootApp.dal.repositories.BracketRepository;
import com.labospring.LaboFootApp.dl.entities.Bracket;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.Team;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.enums.MatchStage;
import com.labospring.LaboFootApp.dl.enums.TournamentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class BracketServiceImpl implements BracketService {
    private final BracketRepository bracketRepository;
    private final TournamentService tournamentService;
    private final BracketGeneratorService bracketGeneratorService;
    private final FootMatchBracketGeneratorService footMatchBracketGeneratorService;

    @Override
    public List<Bracket> getListByTournament(Long tournamentId) {
        Tournament tournament = tournamentService.getOne(tournamentId);
        return bracketRepository.findAllByTournament(tournament);
    }

    public Integer getBracketPosition(FootMatch footMatch){
        return bracketRepository.findPositionByFootMatch(footMatch);
    }

    @Override
    public void createBracketForTournament (Long tournamentId, List<Team> teams) {
        Tournament tournament = tournamentService.getOne(tournamentId);

        if(tournament.getTournamentStatus() != TournamentStatus.PENDING &&
                tournament.getTournamentStatus() !=TournamentStatus.BUILDING)
            throw new BadStatusTournamentException("Can not change bracket after tournament has started");

        List<Bracket> brackets = haveBracketByTournament(tournamentId) ?
                bracketRepository.findAllByTournament(tournament) :
                bracketGeneratorService.generateBrackets(tournament);

        MatchStage firstMatchStage = bracketGeneratorService.getFirstMatchStage(tournament);
        List<Bracket> firstBrackets = brackets.stream()
                .filter(bracket -> bracket.getMatchStage().equals(firstMatchStage))
                .toList();

        if(!teams.isEmpty())
            footMatchBracketGeneratorService.generateFootMatch(firstBrackets, teams);

        bracketRepository.saveAll(brackets);
    }

    @Override
    public boolean haveBracketByTournament(Long tournamentId) {
        return bracketRepository.existsByTournamentId(tournamentId);
    }

    private MatchStage getFirstMatchStage(Tournament tournament){
        return switch (tournament.getTournamentType()){
            case KNOCKOUT_2 -> MatchStage.FINAL;
            case KNOCKOUT_4 -> MatchStage.SEMI_FINAL;
            case KNOCKOUT_8 -> MatchStage.QUARTER_FINAL;
            case KNOCKOUT_16 -> MatchStage.ROUND_OF_16;
            default -> null;
        };
    }


}
