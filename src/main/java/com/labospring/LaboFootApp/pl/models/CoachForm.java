package com.labospring.LaboFootApp.pl.models;

import com.labospring.LaboFootApp.bll.service.models.CoachBusiness;

public record CoachForm(
        String firstname,
        String lastname
) {
    public CoachBusiness toCoachBusiness() {
        return new CoachBusiness(firstname, lastname);
    }
}
