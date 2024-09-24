package com.labospring.LaboFootApp.bll.service.models;

import com.labospring.LaboFootApp.dl.entities.Address;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.enums.TournamentType;

import java.time.LocalDateTime;
import java.util.ArrayList;

public record TournamentBusiness(
        String title,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String placeName,
        Address address,
        TournamentType tournamentType,
        boolean isClose
        ){

    public Tournament toEntity(){
        Tournament tournament =  new Tournament(
                            title,
                            startDate,
                            endDate,
                            placeName,
                            address,
                            tournamentType,
                            isClose
                            );
        if (!tournament.getTournamentType().getGroups().isEmpty()){
            tournament.setRankingList(new ArrayList<>());
        }
        return tournament;
    }
}
