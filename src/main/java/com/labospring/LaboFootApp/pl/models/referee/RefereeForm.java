package com.labospring.LaboFootApp.pl.models.referee;

import com.labospring.LaboFootApp.bll.service.models.RefereeBusiness;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RefereeForm(
        String firstname,
        @NotNull @NotBlank
        String lastname
) {
    public RefereeBusiness toRefereeBusiness() {
        return new RefereeBusiness(firstname, lastname);
    }
}
