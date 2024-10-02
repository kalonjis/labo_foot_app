package com.labospring.LaboFootApp.bll.service.models.matchactor;

import com.labospring.LaboFootApp.dl.entities.Referee;

public record RefereeBusiness(String firstname, String lastname) {
    public Referee toEntity() {
        return new Referee(firstname, lastname);
    }
}
