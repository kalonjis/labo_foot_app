package com.labospring.LaboFootApp.dl.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@ToString
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode
public class FavoriteTeam {
    @EmbeddedId
    private FavoriteTeamId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("teamId")
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Setter
    private boolean notificationActivated;

    public FavoriteTeam(User user, Team team) {
        this.user = user;
        this.team = team;
        notificationActivated = false;
    }

    @Embeddable
    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class FavoriteTeamId implements Serializable {

        private Long userId;
        private Long teamId;

    }
}
