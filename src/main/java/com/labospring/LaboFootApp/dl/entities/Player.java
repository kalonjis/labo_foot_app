package com.labospring.LaboFootApp.dl.entities;

import com.labospring.LaboFootApp.dl.enums.FieldPosition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    // Constructeur avec les champs de MatchActor + Player
    public Player(Long id, String firstname, String lastname, String playerName, int teamNumber, FieldPosition fieldPosition) {
        super(id, firstname, lastname); // Appelle le constructeur de la classe parente MatchActor
        this.playerName = playerName;
        this.teamNumber = teamNumber;
        this.fieldPosition = fieldPosition;
    }

}
