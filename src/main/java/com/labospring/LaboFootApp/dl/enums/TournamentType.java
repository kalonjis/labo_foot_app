package com.labospring.LaboFootApp.dl.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum TournamentType {
    // Tournois avec phase de groupes
    CHAMPIONS_LEAGUE_32("Champions League", 32, true, 8, 4, false),
    WORLD_CUP_32("World Cup", 32, true, 8, 4, false),
    EUROPEAN_CHAMPIONSHIP_24("European Championship", 24, true, 6, 4, false),
    COPA_AMERICA_16("Copa America", 16, true, 4, 4, false),
    ASIAN_CUP_12("Asian Cup", 12, true, 4, 3, false),
    AFRICAN_CUP_10("African Cup", 10, true, 2, 5, false),

    // Tournois sans phase de groupes
    KNOCKOUT_16("Knockout Tournament 16", 16, false, 0, 0, false),
    KNOCKOUT_8("Knockout Tournament 8", 8, false, 0, 0, false),
    KNOCKOUT_4("Knockout Tournament 4", 4, false, 0, 0, false),
    KNOCKOUT_2("Final Match", 2, false, 0, 0, false),

    // Championnats avec un seul groupe
    CHAMPIONSHIP_4("Championship 4 Teams", 4, true, 1, 4, true),
    CHAMPIONSHIP_6("Championship 6 Teams", 6, true, 1, 6, true),
    CHAMPIONSHIP_8("Championship 8 Teams", 8, true, 1, 8, true),
    CHAMPIONSHIP_10("Championship 10 Teams", 10, true, 1, 10, true),
    CHAMPIONSHIP_12("Championship 12 Teams", 12, true, 1, 12, true),
    CHAMPIONSHIP_16("Championship 16 Teams", 16, true, 1, 16, true),
    CHAMPIONSHIP_18("Championship 18 Teams", 18, true, 1, 18, true),
    CHAMPIONSHIP_20("Championship 20 Teams", 20, true, 1, 20, true);

    private final String title;
    private final int nbTeams;
    private final boolean groupStage;
    private final int nbGroups;
    private final int nbTeamsByGroups;
    private final boolean homeAndAway; // Nouveau champ pour indiquer si les matchs sont en aller-retour
    private final List<Integer> groups; // Liste des groupes, null si pas de phase de groupes

    // Constructeur pour tournois avec groupes
    TournamentType(String title, int nbTeams, boolean groupStage, int nbGroups, int nbTeamsByGroups, boolean homeAndAway) {
        this.title = title;
        this.nbTeams = nbTeams;
        this.groupStage = groupStage;
        this.nbGroups = nbGroups;
        this.nbTeamsByGroups = nbTeamsByGroups;
        this.homeAndAway = homeAndAway;
        if (this.groupStage) {
            this.groups = createGroups(nbGroups);
        } else {
            this.groups = null;
        }
    }

    // Méthode pour générer les groupes
    private static List<Integer> createGroups(int nbGroups) {
        return IntStream.rangeClosed(1, nbGroups).boxed().collect(Collectors.toList());
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

    public boolean isHomeAndAway() {
        return homeAndAway;
    }

    public List<Integer> getGroups() {
        return groups;
    }
}

