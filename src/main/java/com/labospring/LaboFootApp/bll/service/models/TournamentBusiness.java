package com.labospring.LaboFootApp.bll.service.models;

import com.labospring.LaboFootApp.dl.entities.Tournament;

public record TournamentBusiness() {

    public Tournament toEntity(){
        return new Tournament();
    }
}
