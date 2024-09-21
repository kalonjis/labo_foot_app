package com.labospring.LaboFootApp.dl.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor @AllArgsConstructor
@Getter
@Entity
public class Team extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(unique = true, nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.PERSIST)
    @Setter
    @JoinColumn(unique = true, nullable = false)
    private Coach coach;

    @Setter
    @OneToMany(mappedBy = "team", cascade ={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Set<Player> players = new HashSet<>();

    public Team(String name, Coach coach, Set<Player> players) {
        this.name = name;
        this.coach = coach;
        if(players!= null)
            players.forEach(this::addPlayer);

    }

    public void addPlayer(Player player) {
        if(players.add(player))
            player.setTeam(this);
    }

    public void removePlayer(Player player) {
        if(players.remove(player))
            player.setTeam(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id) && Objects.equals(name, team.name) && Objects.equals(coach, team.coach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, coach);
    }

    public void removeAllPlayers() {
        for (Player player : players) {
            player.setTeam(null);
        }
        players.clear();
    }
}
