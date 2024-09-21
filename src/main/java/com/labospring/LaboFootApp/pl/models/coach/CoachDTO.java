package com.labospring.LaboFootApp.pl.models.coach;

import com.labospring.LaboFootApp.dl.entities.Coach;

public record CoachDTO(
        long id,
        String firstname,
        String lastname
) {
    public static CoachDTO fromEntity(Coach coach) {
        return new CoachDTO(coach.getId(), coach.getFirstname(), coach.getLastname());
    }
}
