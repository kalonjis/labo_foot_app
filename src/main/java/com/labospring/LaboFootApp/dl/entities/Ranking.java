package com.labospring.LaboFootApp.dl.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

import static com.labospring.LaboFootApp.dl.consts.RankingPoint.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Ranking  extends BaseEntity{

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

    private int nbMatchPlayed;

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

    public void addNbWin(){
        this.nbWins++;
        this.nbMatchPlayed++;
        calculTotalPoints();
    }

    public void addNbLosse(){
        this.nbLosses++;
        this.nbMatchPlayed++;
        calculTotalPoints();
    }

    public void addNbDraw(){
        this.nbDraws++;
        this.nbMatchPlayed++;
        calculTotalPoints();
    }
    public void removeNbWin(){
        this.nbWins--;
        this.nbMatchPlayed--;
        calculTotalPoints();
    }

    public void removeNbLosse(){
        this.nbLosses--;
        this.nbMatchPlayed--;
        calculTotalPoints();
    }

    public void removeNbDraw(){
        this.nbDraws--;
        this.nbMatchPlayed--;
        calculTotalPoints();
    }


    private void calculTotalPoints(){
        this.totalPoints = (POINT_BY_WINS * nbWins) + (POINT_BY_DRAW * nbDraws) + (POINT_BY_LOSES * nbLosses);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Ranking ranking = (Ranking) o;
        return numGroup == ranking.numGroup && totalPoints == ranking.totalPoints && nbMatchPlayed == ranking.nbMatchPlayed && nbWins == ranking.nbWins && nbLosses == ranking.nbLosses && nbDraws == ranking.nbDraws && goalsFor == ranking.goalsFor && goalsAgainst == ranking.goalsAgainst && goalsDiff == ranking.goalsDiff && Objects.equals(id, ranking.id) && Objects.equals(team, ranking.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, team, numGroup, totalPoints, nbMatchPlayed, nbWins, nbLosses, nbDraws, goalsFor, goalsAgainst, goalsDiff);
    }
}
