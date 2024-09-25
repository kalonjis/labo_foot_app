package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.service.BracketService;
import com.labospring.LaboFootApp.bll.service.FootMatchService;
import com.labospring.LaboFootApp.bll.service.models.FootMatchForBracketBusiness;
import com.labospring.LaboFootApp.dal.repositories.BracketRepository;
import com.labospring.LaboFootApp.dl.entities.Bracket;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.enums.MatchStage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.labospring.LaboFootApp.dl.enums.MatchStage.*;

@Service
@RequiredArgsConstructor
public class BracketServiceImpl implements BracketService {
    private final BracketRepository bracketRepository;
    private final FootMatchService footMatchService;
    private final List<MatchStage> matchStages = List.of(FINAL, SEMI_FINAL, QUARTER_FINAL, ROUND_OF_16);


    @Override
    @Transactional
    public void generateAndSaveBrackets(Tournament tournament) {
        List<Bracket> brackets = createBracket(tournament);
        fillNextMatches(brackets);
        bracketRepository.saveAll(brackets);
    }

    private void fillNextMatches(List<Bracket> brackets) {
        HashMap<MatchStage, List<Bracket>> bracketsByStage = getBracketsByMatchStage(brackets);

        for (Map.Entry<MatchStage, List<Bracket>> stageBracketEntry : bracketsByStage.entrySet()) {
            if(stageBracketEntry.getKey() != FINAL){
                MatchStage nextStage = getNextStage(stageBracketEntry.getKey());

                for (Bracket bracket : stageBracketEntry.getValue()) {
                    Bracket nextBracket = getNextBracket(bracket, bracketsByStage, nextStage);
                    if(nextBracket != null){
                        FootMatch nextFootMatch = nextBracket.getMatch();
                        bracket.getMatch().setNextMatch(nextFootMatch);
                    }
                }
            }
        }
    }

    private static Bracket getNextBracket(Bracket bracket, HashMap<MatchStage, List<Bracket>> bracketsByStage, MatchStage nextStage) {
        int posToNext = (bracket.getPositionBracket() + 1) / 2;
        Bracket nextBracket= bracketsByStage.get(nextStage).stream()
                .filter(bracketNext -> bracketNext.getPositionBracket() == posToNext)
                .findFirst()
                .orElse(null);
        return nextBracket;
    }

    private MatchStage getNextStage(MatchStage previousStage) {
        int index = matchStages.indexOf(previousStage);
        return index > 0 ? matchStages.get(index - 1) : null;
    }


    private List<Bracket> createBracket(Tournament tournament){
        return switch (tournament.getTournamentType()){
            case KNOCKOUT_2 -> createBracketsForLinkedStage(tournament, FINAL);
            case KNOCKOUT_4 -> createBracketsForLinkedStage(tournament, SEMI_FINAL);
            case KNOCKOUT_8 -> createBracketsForLinkedStage(tournament, QUARTER_FINAL);
            case KNOCKOUT_16 -> createBracketsForLinkedStage(tournament, ROUND_OF_16);
            default -> new ArrayList<>();
        };
    }
    private List<Bracket> createBracketsForLinkedStage(Tournament tournament, MatchStage matchStageMax){
        List<Bracket> brackets = new ArrayList<>();
        int index = matchStages.indexOf(matchStageMax);

        if(index >= 0){
            for (int i = index; i >= 0; i--) {
                brackets.addAll(createBracketsForOneStage(tournament, matchStages.get(i)));
            }
        }
        return brackets;
    }

    private List<Bracket> createBracketsForOneStage(Tournament tournament, MatchStage matchStage) {
        int nbPositions = getNbPositionBrackets(matchStage);

        List<Bracket> brackets = new ArrayList<>();
        for (int i = 1; i <= nbPositions; i++) {
            Bracket bracket = new Bracket();
            bracket.setPositionBracket(i);
            bracket.setMatchStage(matchStage);
            bracket.setTournament(tournament);

            FootMatch footMatch = footMatchService.buildMatchForBracket(new FootMatchForBracketBusiness(tournament, matchStage));
            bracket.setMatch(footMatch);
            brackets.add(bracket);
        }

        return brackets;

    }

    private int getNbPositionBrackets(MatchStage matchStage){
        return matchStage.getNbPositions();
    }

    public HashMap<MatchStage, List<Bracket>> getBracketsByMatchStage(List<Bracket> brackets) {
        return brackets.stream()
                .collect(Collectors.groupingBy(
                        Bracket::getMatchStage,
                        HashMap::new,
                        Collectors.toList()
                ));
    }
}
