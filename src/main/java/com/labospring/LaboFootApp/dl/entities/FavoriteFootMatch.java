package com.labospring.LaboFootApp.dl.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode
public class FavoriteFootMatch {

    @EmbeddedId
    private FavoriteFootMatchId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("teamId")
    @JoinColumn(name = "foot_match_id", nullable = false)
    private FootMatch footMatch;

    @Embeddable
    @Getter @Setter @ToString
    @AllArgsConstructor @NoArgsConstructor
    public class FavoriteFootMatchId {

        private Long userId;
        private Long teamId;

    }
}

