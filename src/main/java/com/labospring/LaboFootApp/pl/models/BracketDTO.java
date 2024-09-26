package com.labospring.LaboFootApp.pl.models;

import com.labospring.LaboFootApp.dl.entities.Bracket;
import com.labospring.LaboFootApp.dl.enums.MatchStage;
import com.labospring.LaboFootApp.pl.models.footmatch.FootMatchListDetailsDTO;

public record BracketDTO(Long id,
                         FootMatchListDetailsDTO footmatch,
                         MatchStage matchStage,
                         Integer positionBracket) {
    public static BracketDTO fromEntity(Bracket bracket){
        return new BracketDTO(bracket.getId(),
                FootMatchListDetailsDTO.fromEntity(bracket.getMatch()),
                bracket.getMatchStage(),
                bracket.getPositionBracket());
    }
}
