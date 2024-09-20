package com.labospring.LaboFootApp.dl.entities;

import com.labospring.LaboFootApp.dl.enums.FieldPosition;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
public class Player extends MatchActor{

    @Column(nullable = false, length = 15)
    private String playerName;

    private Integer teamNumber;

    @Enumerated(EnumType.STRING)
    private FieldPosition fieldPosition;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "team_id")
    private Team team;

    // Constructeur avec les champs de MatchActor + Player
    public Player(Long id, String firstname, String lastname, String playerName, Integer teamNumber, FieldPosition fieldPosition) {
        super(id, firstname, lastname); // Appelle le constructeur de la classe parente MatchActor
        this.playerName = playerName;
        this.teamNumber = teamNumber;
        this.fieldPosition = fieldPosition;
    }

    public void changeTeam(Team team) {
        if (team != null) {
            team.addPlayer(this);
        }
        else {
            if(this.team != null){
                this.team.removePlayer(this);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Player player = (Player) o;
        return teamNumber == player.teamNumber && Objects.equals(playerName, player.playerName) && fieldPosition == player.fieldPosition && Objects.equals(team, player.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), playerName, teamNumber, fieldPosition, team);
    }
}
