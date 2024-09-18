package com.labospring.LaboFootApp.dl.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Entity
public class TournamentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false)
    private int nbTeams;

    @Setter
    @Column(nullable = false)
    private int nbPlayersOnFieldByTeam;

    @Setter
    @Column(nullable = false)
    private Boolean groupStage;
}