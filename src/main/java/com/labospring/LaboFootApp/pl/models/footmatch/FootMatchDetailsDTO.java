package com.labospring.LaboFootApp.pl.models.footmatch;

import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.enums.MatchStatus;
import com.labospring.LaboFootApp.pl.models.referee.RefereeDTO;
import com.labospring.LaboFootApp.pl.models.team.TeamDTO;

import java.time.LocalDateTime;

public record FootMatchDetailsDTO(
        Long id,
        TeamDTO teamHome,
        TeamDTO teamAway,
        RefereeDTO refereeDTO,
        LocalDateTime matchDate,
        String fieldLocation,
        int scoreTeamHome,
        int scoreTeamAway,
        MatchStatus matchStatus,
        String matchStage) {


    public static FootMatchDetailsDTO fromEntity(FootMatch footMatch) {
        return new FootMatchDetailsDTO(
                footMatch.getId(),
                TeamDTO.fromEntity(footMatch.getTeamHome()),
                TeamDTO.fromEntity(footMatch.getTeamAway()),
                RefereeDTO.fromEntity(footMatch.getReferee()),
                footMatch.getMatchDateTime(),
                footMatch.getFieldLocation(),
                footMatch.getScoreTeamHome(),
                footMatch.getScoreTeamAway(),
                footMatch.getMatchStatus(),
                footMatch.getMatchStage()
        );
    }
}
