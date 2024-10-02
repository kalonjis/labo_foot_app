package com.labospring.LaboFootApp.il.utils;

import com.labospring.LaboFootApp.bll.exceptions.IncorrectAcceptedTeamsSizeException;
import com.labospring.LaboFootApp.bll.service.ParticipatingTeamService;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.ParticipatingTeam;
import com.labospring.LaboFootApp.dl.entities.Team;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.enums.MatchStatus;
import com.labospring.LaboFootApp.dl.enums.SubscriptionStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChampionshipCalendarGenerator {

    public static List<FootMatch> generateMatchSchedule(Tournament tournament, ParticipatingTeamService ptS) {
        // Validate the tournament type
        if (!tournament.getTournamentType().name().startsWith("CHAMPIONSHIP_")) {
            throw new IllegalArgumentException("This method only works for CHAMPIONSHIP type tournaments.");
        }

        // Retrieve the accepted participating teams
        List<ParticipatingTeam> participatingTeams = ptS.getAllByTournamentAndStatus(tournament.getId(), SubscriptionStatus.ACCEPTED);
        int teamCount = participatingTeams.size();

        if (teamCount < 2) {
            throw new IncorrectAcceptedTeamsSizeException("Not enough teams have been accepted to create a match schedule.");
        }

        // Determine if it is a home and away tournament
        boolean homeAndAway = tournament.getTournamentType().isHomeAndAway();

        // Initialize matches list
        List<FootMatch> matches = new ArrayList<>();

        // Generate the schedule
        LocalDateTime tournamentStartDate = tournament.getStartDate();
        LocalDateTime tournamentEndDate = tournament.getEndDate();
        LocalDateTime currentMatchDateTime = tournamentStartDate.plusDays(1); // Start one day after the start date

        // Create a list of teams for easier access
        List<Team> teams = new ArrayList<>();
        for (ParticipatingTeam participatingTeam : participatingTeams) {
            teams.add(participatingTeam.getTeam());
        }

        // Total number of matches for the first round-robin phase
        int totalMatches = (teamCount - 1) * teamCount / 2;

        // Create a set to track unique matches
        Set<String> matchKeys = new HashSet<>();

        // Phase Aller
        for (int teamHomeIndex = 0; teamHomeIndex < teamCount; teamHomeIndex++) {
            for (int teamAwayIndex = teamHomeIndex + 1; teamAwayIndex < teamCount; teamAwayIndex++) {
                Team teamHome = teams.get(teamHomeIndex);
                Team teamAway = teams.get(teamAwayIndex);

                // Generate match keys for home matches
                String matchKeyHome = teamHome.getId() + "-" + teamAway.getId();
                // Create home match
                if (!matchKeys.contains(matchKeyHome)) {
                    FootMatch matchHome = createMatch(teamHome, teamAway, tournament, currentMatchDateTime);
                    matches.add(matchHome);
                    matchKeys.add(matchKeyHome);
                    currentMatchDateTime = currentMatchDateTime.plusDays(1);
                }

                // Vérification de la date de fin du tournoi
                if (currentMatchDateTime.isAfter(tournamentEndDate)) {
                    throw new IllegalStateException("Not enough time to schedule all matches within the tournament dates.");
                }
            }
        }

        // Phase Retour uniquement si homeAndAway est vrai
        if (homeAndAway) {
            currentMatchDateTime = tournamentStartDate.plusDays(1 + totalMatches); // Commencer après tous les matchs aller
            for (int teamHomeIndex = 0; teamHomeIndex < teamCount; teamHomeIndex++) {
                for (int teamAwayIndex = teamHomeIndex + 1; teamAwayIndex < teamCount; teamAwayIndex++) {
                    Team teamHome = teams.get(teamHomeIndex);
                    Team teamAway = teams.get(teamAwayIndex);

                    // Generate match keys for away matches
                    String matchKeyAway = teamAway.getId() + "-" + teamHome.getId();
                    // Create away match
                    if (!matchKeys.contains(matchKeyAway)) {
                        FootMatch matchAway = createMatch(teamAway, teamHome, tournament, currentMatchDateTime);
                        matches.add(matchAway);
                        matchKeys.add(matchKeyAway);
                        currentMatchDateTime = currentMatchDateTime.plusDays(1);
                    }

                    // Vérification de la date de fin du tournoi
                    if (currentMatchDateTime.isAfter(tournamentEndDate)) {
                        throw new IllegalStateException("Not enough time to schedule all matches within the tournament dates.");
                    }
                }
            }
        }

        return matches;
    }

    private static FootMatch createMatch(Team teamHome, Team teamAway, Tournament tournament, LocalDateTime matchDateTime) {
        FootMatch match = new FootMatch();
        match.setTeamHome(teamHome);
        match.setTeamAway(teamAway);
        match.setTournament(tournament);
        match.setMatchDateTime(matchDateTime);
        match.setMatchStatus(MatchStatus.SCHEDULED);
        return match;
    }
}
