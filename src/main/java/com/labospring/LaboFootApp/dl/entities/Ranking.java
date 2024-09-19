package com.labospring.LaboFootApp.dl.entities;


import jakarta.persistence.*;
import lombok.*;

import static com.labospring.LaboFootApp.dl.consts.RankingPoint.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Entity
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    private Team team;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Tournament tournament;

    private int numGroup;

    private int totalPoints;

    private int nbWins;

    private int nbLosses;

    private int nbDraws;

    private int goalsFor;

    private int goalsAgainst;

    private int goalsDiff;

    private void calculGoalsDiff() {
        goalsDiff = goalsFor - goalsAgainst;
    }

    public void setGoalsFor(int goalsFor){
        this.goalsFor = goalsFor;
        calculGoalsDiff();
    }

    public void setGoalsAgainst(int goalsAgainst){
        this.goalsAgainst = goalsAgainst;
        calculGoalsDiff();
    }

    public void addGoalsFor(int goalsForAdd){
        this.goalsFor += goalsForAdd;
        calculGoalsDiff();
    }

    public void addGoalsAgainst(int goalsAgainstAdd){
        this.goalsAgainst += goalsAgainst;
        calculGoalsDiff();
    }

    public void setNbWins(int nbWins) {
        this.nbWins = nbWins;
        calculTotalPoints();
    }

    public void setNbLosses(int nbLosses) {
        this.nbLosses = nbLosses;
        calculTotalPoints();
    }

    public void setNbDraws(int nbDraws) {
        this.nbDraws = nbDraws;
        calculTotalPoints();
    }

    public void addNbWins(int nbWinsAdd){
        this.nbWins += nbWinsAdd;
        calculTotalPoints();
    }

    public void addNbLosses(int nbLossesAdd){
        this.nbLosses += nbLossesAdd;
        calculTotalPoints();
    }

    public void addNbDraws(int nbDrawsAdd){
        this.nbDraws += nbDrawsAdd;
        calculTotalPoints();
    }


    private void calculTotalPoints(){
        this.totalPoints = (POINT_BY_WINS * nbWins) + (POINT_BY_DRAW * nbDraws) + (POINT_BY_LOSES * nbLosses);
    }

}
