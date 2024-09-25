package com.labospring.LaboFootApp.pl.models.ranking;

import com.labospring.LaboFootApp.bll.service.models.RankingBusiness;
import com.labospring.LaboFootApp.dl.entities.Team;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import jakarta.persistence.*;
import lombok.Setter;

public record RankingForm(
        Long tournament_id,
        int numGroup
        ){
    public RankingBusiness toRankingBusiness(){
        return new RankingBusiness(tournament_id, numGroup);
    }
}
