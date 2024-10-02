package com.labospring.LaboFootApp.bll.service.impl.bracket;

import com.labospring.LaboFootApp.bll.exceptions.IncorrectTeamSizeException;
import com.labospring.LaboFootApp.bll.exceptions.MultipleStagesException;
import com.labospring.LaboFootApp.bll.service.FootMatchBracketGeneratorService;
import com.labospring.LaboFootApp.dl.entities.Bracket;
import com.labospring.LaboFootApp.dl.entities.Team;
import com.labospring.LaboFootApp.dl.enums.MatchStage;
import com.labospring.LaboFootApp.il.utils.RandomGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FootMatchBracketGeneratorServiceImpl implements FootMatchBracketGeneratorService {

    /**
     * Generates foot matches by filling the provided brackets with the given teams.
     * It checks if the brackets and teams are valid before proceeding.
     *
     * @param brackets - the list of brackets in which matches will be generated
     * @param teams - the list of teams that will be assigned to the brackets
     * @throws RuntimeException if brackets or teams are null or empty,
     * or if there are brackets from multiple stages
     */
    @Override
    public void generateFootMatch(List<Bracket> brackets, List<Team> teams) {
        // Validate input: ensure neither brackets nor teams are null or empty
        if (brackets == null || teams == null) {
            throw new NullPointerException("Can't generate brackets with no brackets or teams");
        }

        // Ensure all brackets belong to the same match stage
        if (!isAllSameStage(brackets)) {
            throw new MultipleStagesException("Can't generate brackets on multiple stages");
        }

        // Fill the brackets with the teams
        fillBracketsWithTeams(brackets, teams);
    }

    /**
     * Fills the brackets with teams, ensuring that teams are randomly assigned as home and away.
     *
     * @param brackets - the list of brackets to fill with teams
     * @param teams - the list of teams to assign to the brackets
     * @throws RuntimeException if the number of teams is not exactly twice the number of brackets
     */
    private void fillBracketsWithTeams(List<Bracket> brackets, List<Team> teams) {
        // Validate the number of teams; there should be exactly 2 teams per bracket
        if (!isTeamsSizeEnough(brackets, teams)) {
            throw new IncorrectTeamSizeException("Teams are not enough or too much [teams size: " + teams.size() + "]");
        }

        // Create a copy of the teams list to randomly assign teams to brackets
        ArrayList<Team> availableTeams = new ArrayList<>(teams);
        for (Bracket bracket : brackets) {
            // Randomly assign a team as the home team
            Team team = availableTeams.remove(RandomGenerator.randomize(availableTeams.size()));
            bracket.getMatch().setTeamHome(team);

            // Randomly assign a team as the away team
            team = availableTeams.remove(RandomGenerator.randomize(availableTeams.size()));
            bracket.getMatch().setTeamAway(team);
        }
    }

    /**
     * Checks if the number of teams is exactly double the number of brackets.
     * This ensures that each bracket has two teams.
     *
     * @param brackets - the list of brackets
     * @param teams - the list of teams
     * @return true if the number of teams is sufficient (double the brackets), false otherwise
     */
    private boolean isTeamsSizeEnough(List<Bracket> brackets, List<Team> teams) {
        return (brackets.size() * 2) == teams.size();
    }

    /**
     * Checks if all brackets are for the same match stage.
     *
     * @param brackets - the list of brackets to check
     * @return true if all brackets have the same match stage, false if there are different stages
     */
    private boolean isAllSameStage(List<Bracket> brackets) {
        if (brackets.isEmpty()) return true;

        MatchStage matchStage = brackets.getFirst().getMatchStage();

        return brackets.stream().allMatch(bracket -> matchStage.equals(bracket.getMatchStage()));
    }
}
