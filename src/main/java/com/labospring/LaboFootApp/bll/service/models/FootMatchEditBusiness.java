package com.labospring.LaboFootApp.bll.service.models;

import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.Referee;
import com.labospring.LaboFootApp.dl.entities.Team;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.enums.MatchStage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FootMatchEditBusiness {
    Long teamHomeId;
    Long teamAwayId;
    Long refereeId;
    LocalDateTime matchDateTime;
    String fieldLocation;
    MatchStage matchStage;

    public FootMatch toEntity(Team teamHome, Team teamAway, Tournament tournament, Referee referee) {
        return new FootMatch(teamHome, teamAway, tournament, referee,matchDateTime, fieldLocation, matchStage);
    }
}
