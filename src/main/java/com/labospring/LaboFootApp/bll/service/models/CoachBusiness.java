package com.labospring.LaboFootApp.bll.service.models;

import com.labospring.LaboFootApp.dl.entities.Coach;

public record CoachBusiness(String firstname, String lastname) {
    public Coach toEntity() {
        return new Coach(firstname, lastname);
    }
}
