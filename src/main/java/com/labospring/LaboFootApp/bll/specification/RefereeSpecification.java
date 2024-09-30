package com.labospring.LaboFootApp.bll.specification;

import com.labospring.LaboFootApp.dl.entities.Referee;
import org.springframework.data.jpa.domain.Specification;


public class RefereeSpecification {
    public static Specification<Referee> hasFirstname(String firstname){
        return (root, _, builder) ->
                builder.like(builder.lower(root.get("firstname")), "%" + firstname.toLowerCase() + "%");
    }

    public static Specification<Referee> hasLastname(String lastname) {
        return (root, _, builder) ->
                builder.like(builder.lower(root.get("lastname")), "%" + lastname.toLowerCase() + "%");
    }
}
