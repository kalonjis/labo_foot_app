package com.labospring.LaboFootApp.bll.service.models;

import java.time.LocalDateTime;

public record FootMatchSpecificationDTO(
        String team1,
        String team2,
        LocalDateTime before,
        LocalDateTime after,
        String referee,
        String fieldLocation,
        String tournament_title
) {}
