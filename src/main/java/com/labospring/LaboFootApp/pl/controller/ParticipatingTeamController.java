package com.labospring.LaboFootApp.pl.controller;


import com.labospring.LaboFootApp.bll.service.ParticipatingTeamService;
import com.labospring.LaboFootApp.pl.models.participatingTeam.participatingTeamDTO;
import com.labospring.LaboFootApp.pl.models.tournament.TournamentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/participatingteam")
public class ParticipatingTeamController {

    private final ParticipatingTeamService participatingTeamService;

    @GetMapping
    public ResponseEntity<List<participatingTeamDTO>> getAll(){
        return ResponseEntity.ok(
                participatingTeamService.getAll().stream()
                        .map(participatingTeamDTO::fromEntity)
                        .toList()
        );
    }

}
