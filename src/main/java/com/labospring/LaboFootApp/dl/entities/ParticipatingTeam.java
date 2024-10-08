package com.labospring.LaboFootApp.dl.entities;

import com.labospring.LaboFootApp.dl.enums.SubscriptionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Getter
@Entity
public class ParticipatingTeam extends BaseEntity{

    @EmbeddedId
    private ParticipatingTeamId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("tournamentId")
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @MapsId("teamId")
    @JoinColumn(name = "team_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Team team;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus subscriptionStatus;

    @Embeddable
    @Getter @Setter @ToString
    @AllArgsConstructor @NoArgsConstructor
    public static class ParticipatingTeamId implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        private Long tournamentId;
        private Long teamId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ParticipatingTeamId that = (ParticipatingTeamId) o;
            return Objects.equals(tournamentId, that.tournamentId) && Objects.equals(teamId, that.teamId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tournamentId, teamId);
        }
    }

    public ParticipatingTeam(Tournament tournament, Team team){
        this.id = new ParticipatingTeamId(tournament.getId(), team.getId());
        this.tournament = tournament;
        this.team = team;
        this.subscriptionStatus = SubscriptionStatus.PENDING;
    }

}
