package com.labospring.LaboFootApp.pl.models.footmatch.form;

import com.labospring.LaboFootApp.bll.service.models.FootMatchCreateBusiness;
import com.labospring.LaboFootApp.dl.enums.MatchStage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record FootMatchPostForm(Long teamHomeId, @NotNull Long teamAwayId, @NotNull Long tournamentId, Long refereeId, LocalDateTime matchDateTime,
                                @NotBlank String fieldLocation, @NotNull MatchStage matchStage) {
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

