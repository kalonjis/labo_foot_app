package com.labospring.LaboFootApp.bll.specification;

import com.labospring.LaboFootApp.dl.entities.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> hasUsername(String username) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("username")), "%" + username.toLowerCase() + "%");
    }

    public static Specification<User> hasFirstname(String firstname) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("firstname")), "%" + firstname.toLowerCase() + "%");
    }

    public static Specification<User> hasLastname(String lastname) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("lastname")), "%" + lastname.toLowerCase() + "%");
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }
}