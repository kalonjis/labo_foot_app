package com.labospring.LaboFootApp.dl.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

import static com.labospring.LaboFootApp.dl.consts.RankingPoint.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "ranking",
        uniqueConstraints = @UniqueConstraint(name = "UK_team_tournament", columnNames = {"team_id", "tournament_id"}))
public class Ranking  extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    private Team team;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Tournament tournament;

    @Setter
    private int numGroup;
    @Setter
    private int rankingPosition;
    @Setter
    private int totalPoints;
    @Setter
    private int nbMatchPlayed;
    @Setter
    private int nbWins;
    @Setter
    private int nbLosses;
    @Setter
    private int nbDraws;
    @Setter
    private int goalsFor;
    @Setter
    private int goalsAgainst;
    @Setter
    private int goalsDiff;

    public Ranking(Tournament tournament, int numGroup) {
        this.tournament = tournament;
        this.numGroup = numGroup;
    }

    public Ranking(Tournament tournament, Team team) {
        this.tournament = tournament;
        this.team = team;
    }

    public Ranking(Team team, Tournament tournament, int numGroup) {
        this.team = team;
        this.tournament = tournament;
        this.numGroup = numGroup;
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
