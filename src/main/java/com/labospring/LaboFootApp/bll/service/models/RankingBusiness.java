package com.labospring.LaboFootApp.bll.service.models;

import com.labospring.LaboFootApp.dl.entities.Ranking;
import com.labospring.LaboFootApp.dl.entities.Tournament;


public record RankingBusiness(
        Long tournament_id,
        int numGroup
) {
//    public Ranking toEntity(){
//        Tournament t =
//        return new Ranking(tournament_id, numGroup);
//    }
}
