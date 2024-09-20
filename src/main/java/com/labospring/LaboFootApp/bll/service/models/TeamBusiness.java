package com.labospring.LaboFootApp.bll.service.models;

import com.labospring.LaboFootApp.dl.entities.Team;

import java.util.List;
import java.util.stream.Collectors;

public record TeamBusiness(
        String name,
        CoachBusiness coach,
        List<PlayerBusiness> players
) {

    public Team toEntity(){
        return new Team(
                name,
                coach.toEntity(),
                players.stream().map(PlayerBusiness::toEntity).collect(Collectors.toSet()));
    }
}
