package com.labospring.LaboFootApp.dl.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false) @ToString
@Getter
@Entity
public class Team extends BaseEntity{
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
    @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Player> players = new HashSet<>();;


    public void addPlayer(Player player) {
        players.add(player);
        player.setTeam(this);
    }

    public void removePlayer(Player player) {
        players.remove(player);
        player.setTeam(null);
    }

}
