package com.labospring.LaboFootApp.dl.entities;

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
    @JoinColumn(nullable = false)
    private Team teamHome;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Team teamAway;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Tournament tournament;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
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

    @Setter
    private String matchStage;
}
