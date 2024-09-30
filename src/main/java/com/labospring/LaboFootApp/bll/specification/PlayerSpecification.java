package com.labospring.LaboFootApp.bll.specification;

import com.labospring.LaboFootApp.dl.entities.Player;
import com.labospring.LaboFootApp.dl.enums.FieldPosition;
import org.springframework.data.jpa.domain.Specification;

public class PlayerSpecification {
    public static Specification<Player> hasFirstname(String firstname) {
        return (root, _, builder) ->
                builder.like(builder.lower(root.get("firstname")), "%" + firstname.toLowerCase() + "%");
    }

    public static Specification<Player> hasLastname(String lastname) {
        return (root, _, builder) ->
                builder.like(builder.lower(root.get("lastname")), "%" + lastname.toLowerCase() + "%");
    }

    public static Specification<Player> hasPlayerName(String playerName) {
        return (root, _, builder) ->
                builder.like(builder.lower(root.get("playerName")), "%" + playerName.toLowerCase() + "%");
    }


    public static Specification<Player> hasFieldPosition(FieldPosition fieldPosition) {
        return (root, _, builder) -> builder.equal(root.get("fieldPosition"), fieldPosition);
    }

    public static Specification<Player> hasTeamNumber(Integer teamNumber) {
        return (root, _, builder) ->
                builder.equal(root.get("teamNumber"), teamNumber);
    }

}
