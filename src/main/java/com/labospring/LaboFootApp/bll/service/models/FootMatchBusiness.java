package com.labospring.LaboFootApp.bll.service.models;

import com.labospring.LaboFootApp.dl.entities.FootMatch;

import java.time.LocalDateTime;

public record FootMatchBusiness(Long teamHomeId, Long teamAwayId, Long tournamentId, Long refereeId, LocalDateTime matchDateTime,
                                String fieldLocation) {
    public FootMatch toEntity() {
        return new FootMatch();
    }
}
