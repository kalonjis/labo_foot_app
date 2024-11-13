package com.labospring.LaboFootApp.pl.models.footmatch;

import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.enums.MatchStage;
import com.labospring.LaboFootApp.dl.enums.MatchStatus;
import com.labospring.LaboFootApp.pl.models.referee.RefereeDTO;
import com.labospring.LaboFootApp.pl.models.team.TeamDTO;
import com.labospring.LaboFootApp.pl.models.tournament.TournamentSmallDetailsDTO;

import java.time.LocalDateTime;

public record FootMatchDetailsDTO(
        Long id,
        TeamDTO teamHome,
        TeamDTO teamAway,
        TournamentSmallDetailsDTO tournamentSmallDetailsDTO,
        RefereeDTO refereeDTO,
        LocalDateTime matchDate,
        String fieldLocation,
        int scoreTeamHome,
        int scoreTeamAway,
        MatchStatus matchStatus,
        MatchStage matchStage) {


    public static FootMatchDetailsDTO fromEntity(FootMatch footMatch) {
        return new FootMatchDetailsDTO(
                footMatch.getId(),
                TeamDTO.fromEntity(footMatch.getTeamHome()),
                TeamDTO.fromEntity(footMatch.getTeamAway()),
                TournamentSmallDetailsDTO.fromEntity(footMatch.getTournament()),
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
