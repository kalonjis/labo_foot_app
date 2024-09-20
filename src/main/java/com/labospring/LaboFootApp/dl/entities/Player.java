package com.labospring.LaboFootApp.dl.entities;

import com.labospring.LaboFootApp.dl.enums.FieldPosition;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@Getter @Setter
@Entity
public class Player extends MatchActor{

    @Column(nullable = false, length = 15)
    private String playerName;

    private int teamNumber;

    @Enumerated(EnumType.STRING)
    private FieldPosition fieldPosition;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    // Constructeur avec les champs de MatchActor + Player
    public Player(Long id, String firstname, String lastname, String playerName, int teamNumber, FieldPosition fieldPosition) {
        super(id, firstname, lastname); // Appelle le constructeur de la classe parente MatchActor
        this.playerName = playerName;
        this.teamNumber = teamNumber;
        this.fieldPosition = fieldPosition;
    }

    public void setTeam(Team team) {
        this.team = team;
        if (!team.getPlayers().contains(this)) {
            team.getPlayers().add(this);
        }
    }

}
