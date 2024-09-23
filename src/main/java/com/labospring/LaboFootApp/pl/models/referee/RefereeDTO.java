package com.labospring.LaboFootApp.pl.models.referee;

import com.labospring.LaboFootApp.dl.entities.Referee;

public record RefereeDTO(
        long id,
        String firstname,
        String lastname
) {
    public static RefereeDTO fromEntity(Referee referee) {
        return new RefereeDTO(referee.getId(), referee.getFirstname(), referee.getLastname());
    }
}
