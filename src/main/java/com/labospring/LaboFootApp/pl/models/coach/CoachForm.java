package com.labospring.LaboFootApp.pl.models.coach;

import com.labospring.LaboFootApp.bll.service.models.matchactor.CoachBusiness;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CoachForm(
        String firstname,
        @NotNull @NotBlank
        String lastname
) {
    public CoachBusiness toCoachBusiness() {
        return new CoachBusiness(firstname, lastname);
    }
}
