package com.labospring.LaboFootApp.bll.specification;

import com.labospring.LaboFootApp.dl.entities.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

//scoreTeamHome
//scoreTeamAway
public class FootMatchSpecification {

    // Search if team name is either in teamHome or teamAway
    public static Specification<FootMatch> hasTeamName(String teamName) {
        return (root, _, builder) -> {
            // Join with the teamHome and teamAway entities
            Join<FootMatch, Team> teamHomeJoin = root.join("teamHome", JoinType.INNER);
            Join<FootMatch, Team> teamAwayJoin = root.join("teamAway", JoinType.INNER);

            // Build condition for teamHome or teamAway having the specified name
            return builder.or(
                    builder.like(builder.lower(teamHomeJoin.get("name")), "%" + teamName.toLowerCase() + "%"),
                    builder.like(builder.lower(teamAwayJoin.get("name")), "%" + teamName.toLowerCase() + "%")
            );
        };
    }

    public static Specification<FootMatch> hasMatchDateTimeAfter(LocalDateTime localDateTime) {
        return (root, _, builder) ->
                builder.greaterThanOrEqualTo(root.get("matchDateTime"), localDateTime);
    }

    public static Specification<FootMatch> hasMatchDateTimeBefore(LocalDateTime localDateTime) {
        return (root, _, builder) ->
                builder.lessThanOrEqualTo(root.get("matchDateTime"), localDateTime);
    }

    public static Specification<FootMatch> hasFieldLocation(String fieldLocation) {
        return (root, _, builder) ->
                builder.like(builder.lower(root.get("fieldLocation")),"%" + fieldLocation.toLowerCase() + "%");
    }

    public static Specification<FootMatch> hasRefereeName(String refereeName) {
        return (root, _, builder) -> {
            Join<FootMatch, Referee> refereeJoin = root.join("referee", JoinType.INNER);
            return builder.or(
                    builder.like(builder.lower(refereeJoin.get("firstname")), "%" + refereeName.toLowerCase() + "%"),
                    builder.like(builder.lower(refereeJoin.get("lastname")), "%" + refereeName.toLowerCase() + "%")
                    );
        };
    }
}

