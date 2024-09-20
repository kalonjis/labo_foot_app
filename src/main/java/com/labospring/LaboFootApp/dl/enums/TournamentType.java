package com.labospring.LaboFootApp.dl.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum TournamentType {
    // Tournois avec phase de groupes
    CHAMPIONS_LEAGUE_32("Champions League", 32, true, 8, 4),
    WORLD_CUP_32("World Cup", 32, true, 8, 4),
    EUROPEAN_CHAMPIONSHIP_24("European Championship", 24, true, 6, 4),
    COPA_AMERICA_16("Copa America", 16, true, 4, 4),
    ASIAN_CUP_12("Asian Cup", 12, true, 4, 3),
    AFRICAN_CUP_10("African Cup", 10, true, 2, 5),

    // Tournois sans phase de groupes
    KNOCKOUT_16("Knockout Tournament 16", 16, false, 0, 0),
    KNOCKOUT_8("Knockout Tournament 8", 8, false, 0, 0),
    KNOCKOUT_4("Knockout Tournament 4", 4, false, 0, 0),
    KNOCKOUT_2("Final Match", 2, false, 0, 0);

    private final String title;
    private final int nbTeams;
    private final boolean groupStage;
    private final int nbGroups;
    private final int nbTeamsByGroups;
    private final List<Integer> groups; // Liste des groupes, null si pas de phase de groupes

    // Constructeur pour tournois avec groupes
    TournamentType(String title, int nbTeams, boolean groupStage, int nbGroups, int nbTeamsByGroups) {
        this.title = title;
        this.nbTeams = nbTeams;
        this.groupStage = groupStage;
        this.nbGroups = nbGroups;
        this.nbTeamsByGroups = nbTeamsByGroups;
        if(this.groupStage) {
            this.groups = createGroups(nbGroups);
        }else {
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

    public List<Integer> getGroups() {
        return groups;
    }
}
