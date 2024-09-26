package com.labospring.LaboFootApp.bll.service.impl;

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

    @Override
    public void generateFootMatch(List<Bracket> brackets,  List<Team> teams) {
        if(brackets == null || teams == null || brackets.isEmpty() || teams.isEmpty())
            throw new RuntimeException("Can't generate brackets with no brackets or teams");

        if(!isAllSameStage(brackets))
            throw new RuntimeException("Can't generate brackets on multiple stage");

        if(!isTeamsSizeEnough(brackets, teams))
            throw new RuntimeException("Teams are not enough or too much [teams size: " + teams.size() + "]");

        fillBracketsWithTeams(brackets, teams);
    }

    private void fillBracketsWithTeams(List<Bracket> brackets, List<Team> teams) {
        ArrayList<Team> bracketsList = new ArrayList<>(teams);
        for (Bracket bracket : brackets) {
            Team team = bracketsList.remove(RandomGenerator.randomize(bracketsList.size()));
            bracket.getMatch().setTeamHome(team);
            team = bracketsList.remove(RandomGenerator.randomize(bracketsList.size()));
            bracket.getMatch().setTeamAway(team);
        }
    }

    private boolean isTeamsSizeEnough(List<Bracket> brackets, List<Team> teams) {
        return (brackets.size() * 2) == teams.size();
    }

    private boolean isAllSameStage(List<Bracket> brackets) {
        if (brackets.isEmpty())
            return true;

        MatchStage matchStage = brackets.get(0).getMatchStage();
        return brackets.stream().allMatch(bracket -> matchStage.equals(bracket.getMatchStage()));
    }
}
