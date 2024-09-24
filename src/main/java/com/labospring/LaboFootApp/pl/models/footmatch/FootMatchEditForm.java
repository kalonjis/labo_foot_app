package com.labospring.LaboFootApp.pl.models.footmatch;

import com.labospring.LaboFootApp.bll.service.models.FootMatchEditBusiness;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record FootMatchEditForm(Long teamHomeId, Long teamAwayId, Long refereeId, LocalDateTime matchDateTime,
                            @NotBlank String fieldLocation, @NotBlank String matchStage) {
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
