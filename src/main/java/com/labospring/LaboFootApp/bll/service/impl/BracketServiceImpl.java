package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.service.BracketService;
import com.labospring.LaboFootApp.dal.repositories.BracketRepository;
import com.labospring.LaboFootApp.dl.entities.Bracket;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.enums.MatchStage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.labospring.LaboFootApp.dl.enums.MatchStage.*;

@Service
@RequiredArgsConstructor
public class BracketServiceImpl implements BracketService {
    private final BracketRepository bracketRepository;
    private final List<MatchStage> matchStages = List.of(FINAL, SEMI_FINAL, QUARTER_FINAL, ROUND_OF_16);


    @Override
    @Transactional
    public void generateBracket(Tournament tournament) {
        List<Bracket> bracket = createBracket(tournament);

        bracketRepository.saveAll(bracket);
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
        int index = -1;
        for (int i = 0; i < matchStages.size(); i++){
            if(matchStageMax == matchStages.get(i)){
                index = i;
                break;
            }
        }
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
            brackets.add(bracket);
        }

        return brackets;

    }

    private int getNbPositionBrackets(MatchStage matchStage){
        return switch (matchStage){
            case ROUND_OF_16 -> 8;
            case QUARTER_FINAL -> 4;
            case SEMI_FINAL -> 2;
            case FINAL -> 1;
            default -> 0;
        };
    }
}
