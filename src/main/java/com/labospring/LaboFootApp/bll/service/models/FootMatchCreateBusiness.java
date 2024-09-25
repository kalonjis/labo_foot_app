package com.labospring.LaboFootApp.bll.service.models;

import com.labospring.LaboFootApp.dl.enums.MatchStage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class FootMatchCreateBusiness extends FootMatchEditBusiness {
    private Long tournamentId;

    public FootMatchCreateBusiness(Long teamHomeId, Long teamAwayId, Long refereeId, LocalDateTime matchDateTime, String fieldLocation, MatchStage matchStage, Long tournamentId) {
        super(teamHomeId, teamAwayId, refereeId, matchDateTime, fieldLocation, matchStage);
        this.tournamentId = tournamentId;
    }
}
