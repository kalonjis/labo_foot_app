package com.labospring.LaboFootApp.bll.service.models;

import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.Referee;
import com.labospring.LaboFootApp.dl.entities.Team;
import com.labospring.LaboFootApp.dl.entities.Tournament;

import java.time.LocalDateTime;

public record FootMatchBusiness(Long teamHomeId, Long teamAwayId, Long tournamentId, Long refereeId, LocalDateTime matchDateTime,
                                String fieldLocation, String matchStage) {

    public FootMatch toEntity(Team teamHome, Team teamAway, Tournament tournament, Referee referee) {
        return new FootMatch(teamHome, teamAway, tournament, referee,matchDateTime, fieldLocation, matchStage);
    }
}
