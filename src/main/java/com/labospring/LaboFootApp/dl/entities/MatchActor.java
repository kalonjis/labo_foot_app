package com.labospring.LaboFootApp.dl.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false) @ToString
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class MatchActor extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false, length = 50)
    private String firstname;
    @Setter @Column(nullable = false, length = 50)
    private String lastname;

    public MatchActor(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
