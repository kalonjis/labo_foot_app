package com.labospring.LaboFootApp.dl.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FavoriteFootMatch {

    @EmbeddedId
    private FavoriteFootMatchId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("footMatchId")
    @JoinColumn(name = "foot_match_id", nullable = false)
    private FootMatch footMatch;

    @Setter
    private boolean notificationActivated;

    public FavoriteFootMatch(User user, FootMatch footMatch) {
        this.user = user;
        this.footMatch = footMatch;
        notificationActivated = false;
        this.id = new FavoriteFootMatchId(user.getId(), footMatch.getId());
    }

    @Embeddable
    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class FavoriteFootMatchId implements Serializable {

        private Long userId;
        private Long footMatchId;

    }
}

