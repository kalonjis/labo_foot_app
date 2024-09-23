package com.labospring.LaboFootApp.pl.models.footmatch;

import com.labospring.LaboFootApp.bll.service.models.FootMatchCreateBusiness;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record FootMatchForm(Long teamHomeId, @NotNull Long teamAwayId,@NotNull Long tournamentId, Long refereeId, LocalDateTime matchDateTime,
                            @NotBlank String fieldLocation, @NotBlank String matchStage) {
    public FootMatchCreateBusiness toFootMatchBusiness() {
        return new FootMatchCreateBusiness(
                teamHomeId,
                teamAwayId,
                refereeId,
                matchDateTime,
                fieldLocation,
                matchStage,
                tournamentId
        );
    }
}

