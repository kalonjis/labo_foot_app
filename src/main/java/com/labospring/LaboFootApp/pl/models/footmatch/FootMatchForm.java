package com.labospring.LaboFootApp.pl.models.footmatch;

import com.labospring.LaboFootApp.bll.service.models.FootMatchBusiness;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record FootMatchForm(Long teamHomeId, @NotNull Long teamAwayId,@NotNull Long tournamentId, Long refereeId, LocalDateTime matchDateTime,
                            @NotBlank String fieldLocation) {
    public FootMatchBusiness toFootMatchBusiness() {
        return new FootMatchBusiness(
                teamHomeId,
                teamAwayId,
                tournamentId,
                refereeId,
                matchDateTime,
                fieldLocation
        );
    }
}

