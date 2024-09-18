package com.labospring.LaboFootApp.dl.entities;

import com.labospring.LaboFootApp.dl.enums.FieldPosition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    private FieldPosition fieldPosition;

}
