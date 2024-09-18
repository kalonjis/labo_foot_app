package com.labospring.LaboFootApp.dl.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
@Getter
@Entity
public class Team{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(unique = true, nullable = false)
    private String name;

    @OneToOne
    @Setter
    @JoinColumn(unique = true, nullable = false)
    private Coach coach;

    @Setter
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Player> players;


    public void addPlayer (Player player){
        players.add(player);
    }

    public void removePlayer (Player player){
        players.remove(player);
    }
}
