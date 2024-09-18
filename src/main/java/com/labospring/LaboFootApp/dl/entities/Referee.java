package com.labospring.LaboFootApp.dl.entities;

import jakarta.persistence.Entity;
import lombok.*;


@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@Getter @Setter
@Entity
public class Referee extends MatchActor{
}
