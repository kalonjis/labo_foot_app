package com.labospring.LaboFootApp.pl.models.footmatch;

import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.enums.MatchStatus;
import com.labospring.LaboFootApp.pl.models.team.TeamSmallDetailsDTO;

import java.time.LocalDateTime;


public record FootMatchListDetailsDTO(Long id,
                                      TeamSmallDetailsDTO teamHome,
                                      TeamSmallDetailsDTO teamAway,
                                      int scoreTeamHome,
                                      int scoreTeamAway,
                                      MatchStatus matchStatus,
                                      LocalDateTime dateMatch){

    public static FootMatchListDetailsDTO fromEntity(FootMatch footMatch) {

        return footMatch == null ? null : new FootMatchListDetailsDTO(
                footMatch.getId(),
                TeamSmallDetailsDTO.fromEntity(footMatch.getTeamHome()),
                TeamSmallDetailsDTO.fromEntity(footMatch.getTeamAway()),
                footMatch.getScoreTeamHome(),
                footMatch.getScoreTeamAway(),
                footMatch.getMatchStatus(),
                footMatch.getMatchDateTime());
    }
}
