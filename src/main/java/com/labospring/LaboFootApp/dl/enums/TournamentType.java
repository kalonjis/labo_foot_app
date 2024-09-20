package com.labospring.LaboFootApp.dl.enums;

import java.util.List;

public enum TournamentType {
    WORLD_CUP_32_TEAMS("World Cup", 32, true, 8, 4, null),
    EURO_CUP_24_TEAMS("Euro Cup", 24, true, 6, 4, null),
    SMALL_WORLD_CUP_16_TEAMS("Small World Cup", 16, true, 4, 4, null),
    MINI_TOURNAMENT_8_TEAMS("Mini Tournament", 8, true, 2, 4, null),
    TOURNAMENT_12_TEAMS("Tournament 12 Teams", 12, true, 4, 3, null), // 12 équipes, 4 groupes de 3
    TOURNAMENT_10_TEAMS("Tournament 10 Teams", 10, true, 2, 5, null), // 10 équipes, 2 groupes de 5
    CHAMPIONS_LEAGUE_32_TEAMS("Champions League", 32, true, 8, 4, List.of("Round of 16", "Quarter-Final", "Semi-Final", "Final")), // Phase éliminatoire après la phase de groupe
    KNOCKOUT_TOURNAMENT_16_TEAMS("Knockout Tournament", 16, false, 0, 0, List.of("1/8", "1/4", "1/2", "Finale")),
    KNOCKOUT_TOURNAMENT_8_TEAMS("Knockout Tournament", 8, false, 0, 0, List.of("1/4", "1/2", "Finale")),
    KNOCKOUT_TOURNAMENT_4_TEAMS("Knockout Tournament", 4, false, 0, 0, List.of("1/2", "Finale")),
    KNOCKOUT_TOURNAMENT_32_TEAMS("Knockout Tournament", 32, false, 0, 0, List.of("1/16", "1/8", "1/4", "1/2", "Finale")),
    KNOCKOUT_TOURNAMENT_64_TEAMS("Knockout Tournament", 64, false, 0, 0, List.of("1/32", "1/16", "1/8", "1/4", "1/2", "Finale"));

    private final String title;
    private final int nbTeams;
    private final boolean groupStage;
    private final int nbGroups;
    private final int nbTeamsByGroups;
    private final List<String> finalPhases;

    TournamentType(String title, int nbTeams, boolean groupStage, int nbGroups, int nbTeamsByGroups, List<String> finalPhases) {
        this.title = title;
        this.nbTeams = nbTeams;
        this.groupStage = groupStage;
        this.nbGroups = nbGroups;
        this.nbTeamsByGroups = nbTeamsByGroups;
        this.finalPhases = finalPhases;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public int getNbTeams() {
        return nbTeams;
    }

    public boolean isGroupStage() {
        return groupStage;
    }

    public int getNbGroups() {
        return nbGroups;
    }

    public int getNbTeamsByGroups() {
        return nbTeamsByGroups;
    }

    public List<String> getFinalPhases() {
        return finalPhases;
    }

    // Méthode pour obtenir la description du tournoi
    public String getDescription() {
        if (groupStage) {
            return String.format("%s: %d équipes réparties en %d groupes de %d équipes", title, nbTeams, nbGroups, nbTeamsByGroups);
        } else {
            return String.format("%s: %d équipes en phases finales (%s)", title, nbTeams, String.join(", ", finalPhases));
        }
    }
}
