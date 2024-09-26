package com.labospring.LaboFootApp.dl.entities;

import com.labospring.LaboFootApp.dl.enums.MatchStage;
import com.labospring.LaboFootApp.dl.enums.MatchStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Getter
@Entity
public class FootMatch extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Team teamHome;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Team teamAway;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Tournament tournament;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Referee referee;

    @Setter @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime matchDateTime;

    @Setter @Column(length = 50)
    private String fieldLocation;

    @Setter
    private int scoreTeamHome;

    @Setter
    private int scoreTeamAway;

    @Setter
    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private User userModerator;

    @Enumerated(EnumType.STRING)
    @Setter
    private MatchStage matchStage;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private FootMatch nextMatch;

    public FootMatch(Team teamHome, Team teamAway, Tournament tournament, Referee referee, LocalDateTime matchDateTime, String fieldLocation, MatchStage matchStage) {
        this.teamHome = teamHome;
        this.teamAway = teamAway;
        this.tournament = tournament;
        this.referee = referee;
        this.matchDateTime = matchDateTime;
        this.fieldLocation = fieldLocation;
        this.matchStage = matchStage;
    }
}
