package com.labospring.LaboFootApp.bll.specification;

import com.labospring.LaboFootApp.dl.entities.Coach;
import org.springframework.data.jpa.domain.Specification;

public class CoachSpecification {
    public static Specification<Coach> hasFirstname(String firstname) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("firstname")), "%" + firstname.toLowerCase() + "%");
    }

    public static Specification<Coach> hasLastname(String lastname) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("lastname")), "%" + lastname.toLowerCase() + "%");
    }
}
