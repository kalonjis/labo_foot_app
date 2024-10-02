package com.labospring.LaboFootApp.pl.models.footmatch.form;

import com.labospring.LaboFootApp.bll.service.models.footmatch.FootMatchEditBusiness;
import com.labospring.LaboFootApp.dl.enums.MatchStage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record FootMatchEditForm(Long teamHomeId, Long teamAwayId, Long refereeId, LocalDateTime matchDateTime,
                            @NotBlank String fieldLocation, @NotNull MatchStage matchStage) {
    public FootMatchEditBusiness toFootMatchBusiness() {
        return new FootMatchEditBusiness(
                teamHomeId,
                teamAwayId,
                refereeId,
                matchDateTime,
                fieldLocation,
                matchStage
        );
    }
}
