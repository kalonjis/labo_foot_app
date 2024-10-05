package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.service.BracketGeneratorService;
import com.labospring.LaboFootApp.dl.entities.Bracket;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.enums.MatchStage;
import com.labospring.LaboFootApp.dl.enums.MatchStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

import static com.labospring.LaboFootApp.dl.enums.MatchStage.*;

@Service
@RequiredArgsConstructor
public class BracketGeneratorServiceImpl implements BracketGeneratorService {
    // List of tournament stages in descending order (final -> round of 16)
    private final List<MatchStage> matchStages = List.of(FINAL, SEMI_FINAL, QUARTER_FINAL, ROUND_OF_16);

    /**
     * Generates and saves brackets for a tournament.
     * The method first creates brackets based on the tournament type,
     * fills them with the following matches, then saves them in the database.
     *
     * @throws RuntimeException if tournament or type of tournament is null
     * @param tournament the tournament for which brackets are to be generated.
     */
    @Override
    public List<Bracket> generateBrackets(Tournament tournament) {
        if(tournament == null || tournament.getTournamentType() == null)
            throw new RuntimeException("Can't generate bracket");

        List<Bracket> brackets = createBracket(tournament);
        fillNextMatches(brackets);
        return brackets;
    }

    /**
     * Fills each bracket with the next match it is related to (except the final).
     * Brackets are grouped by their stage in the tournament (quarterfinal, semifinal, etc.).
     *
     * @param brackets the list of brackets to process.
     */
    private void fillNextMatches(List<Bracket> brackets) {
        HashMap<MatchStage, List<Bracket>> bracketsByStage = getBracketsByMatchStage(brackets);

        bracketsByStage.entrySet().stream()
                .filter(entry -> entry.getKey() != FINAL)
                .forEach(entry -> {
                    MatchStage nextStage = getNextStage(entry.getKey());

                    entry.getValue()
                            .forEach(bracket -> {
                                Bracket nextBracket = getNextBracket(bracket, bracketsByStage, nextStage);
                                if (nextBracket != null) {
                                    FootMatch nextFootMatch = nextBracket.getMatch();
                                    // Link current match with next match
                                    bracket.getMatch().setNextMatch(nextFootMatch);
                                }
                            });
                });
    }

    /**
     * Returns the next bracket from the current position in the tournament.
     * The logic is based on the current bracket position and the next stage of the tournament.
     *
     * @param bracket the current bracket.
     * @param bracketsByStage a HashMap grouping brackets by stage of the tournament.
     * @param nextStage the next stage of the tournament.
     * @return the next bracket, or null if there is none.
     */
    private Bracket getNextBracket(Bracket bracket, HashMap<MatchStage, List<Bracket>> bracketsByStage, MatchStage nextStage) {
        int nextPosition = (bracket.getPositionBracket() + 1) / 2; // The position we need to look for the next bracket
        Bracket nextBracket= bracketsByStage.get(nextStage).stream()
                .filter(bracketNext -> bracketNext.getPositionBracket() == nextPosition)
                .findFirst()
                .orElse(null);
        return nextBracket;
    }

    /**
     * Return next stage from a current stage
     * @param stage the current stage
     * @return the next stage, or null if there is none.
     */
    private MatchStage getNextStage(MatchStage stage) {
        int index = matchStages.indexOf(stage);
        return index > 0 ? matchStages.get(index - 1) : null;
    }


    /**
     * Creates brackets for a tournament based on its type (KNOCKOUT_2, KNOCKOUT_4, etc.).
     * Each tournament type corresponds to a number of stages, for example, KNOCKOUT_4 starts at the semi-finals.
     *
     * @param tournament the tournament for which the brackets are being created.
     * @return the list of brackets generated for the tournament.
     */
    private List<Bracket> createBracket(Tournament tournament){
        MatchStage matchStage = getFirstMatchStage(tournament);
        return createBracketsForLinkedStage(tournament, matchStage);
    }


    /**
     * Creates brackets for all linked stages of a tournament up to the specified final stage.
     * <p>For example : </p>
     * <ul>
     * <li> If the match stage is SEMI_FINAL, it will generate brackets for the semi-final and final.</li>
     * <li> If the match stage is QUARTER_FINAL, it will generate brackets for quart, semi and final.</li>
     *</ul>
     * @param tournament the current tournament.
     * @param matchStageMax the maximum stage for which brackets should be generated (e.g. QUARTER_FINAL).
     * @return the list of generated brackets.
     */
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

    /**
     * Creates brackets for a single stage of the tournament (e.g. semi-finals).
     * A match is created for each bracket.
     * For exemple, if the match state is QUARTER_FINAL
     * then it will generate the number of brackets needed for that stage.
     *
     * @param tournament the current tournament.
     * @param matchStage the current stage for which brackets are being created.
     * @return the list of brackets created for the given stage.
     */
    private List<Bracket> createBracketsForOneStage(Tournament tournament, MatchStage matchStage) {
        int nbPositions = matchStage.getNbPositions();

        List<Bracket> brackets = new ArrayList<>();
        for (int i = 1; i <= nbPositions; i++) {
            Bracket bracket = new Bracket();
            bracket.setPositionBracket(i);
            bracket.setMatchStage(matchStage);
            bracket.setTournament(tournament);

            FootMatch footMatch = buildMatchForBracket(tournament, matchStage);
            bracket.setMatch(footMatch);
            brackets.add(bracket);
        }

        return brackets;
    }

    /**
     * Groups brackets by their stage in the tournament (final, semifinal, quarterfinal, etc.).
     *
     * @param brackets the list of brackets to group.
     * @return a HashMap where the key is the stage of the tournament and the value is the list of associated brackets.
     */
    public HashMap<MatchStage, List<Bracket>> getBracketsByMatchStage(List<Bracket> brackets) {
        return brackets.stream()
                .collect(Collectors.groupingBy(
                        Bracket::getMatchStage,
                        HashMap::new,
                        Collectors.toList()
                ));
    }


    private FootMatch buildMatchForBracket(Tournament tournament, MatchStage matchStage) {
        if(tournament == null || matchStage == null)
            throw new RuntimeException("Tournament or MatchStage is needed when building a Match for Bracket");

        FootMatch footMatch = new FootMatch();
        footMatch.setMatchStage(matchStage);
        footMatch.setMatchDateTime(tournament.getStartDate());
        footMatch.setTournament(tournament);
        footMatch.setMatchStatus(MatchStatus.SCHEDULED);
        return footMatch;
    }

    @Override
    public MatchStage getFirstMatchStage(Tournament tournament){
        return switch (tournament.getTournamentType()){
            case KNOCKOUT_2 -> MatchStage.FINAL;
            case KNOCKOUT_4 -> MatchStage.SEMI_FINAL;
            case KNOCKOUT_8, COPA_AMERICA_16  -> MatchStage.QUARTER_FINAL;
            case KNOCKOUT_16, WORLD_CUP_32 -> MatchStage.ROUND_OF_16;
            default -> null;
        };
    }

}
