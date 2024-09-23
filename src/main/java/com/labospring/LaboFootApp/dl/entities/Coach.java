package com.labospring.LaboFootApp.dl.entities;

import jakarta.persistence.Entity;
import lombok.*;


@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@Getter @Setter
@Entity
public class Coach extends MatchActor{
    public Coach(String firstName, String lastName){
        super(firstName, lastName);
    }
}