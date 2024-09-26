package com.labospring.LaboFootApp.pl.controller;


import com.labospring.LaboFootApp.bll.service.ParticipatingTeamService;
import com.labospring.LaboFootApp.dl.entities.ParticipatingTeam;
import com.labospring.LaboFootApp.pl.models.participatingTeam.ParticipatingTeamDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/participatingteam")
public class ParticipatingTeamController {

    private final ParticipatingTeamService participatingTeamService;

    @GetMapping
    public ResponseEntity<List<ParticipatingTeamDTO>> getAll(){
        return ResponseEntity.ok(
                participatingTeamService.getAll().stream()
                        .map(ParticipatingTeamDTO::fromEntity)
                        .toList()
        );
    }

    @GetMapping("/by-ids")
    public ResponseEntity<ParticipatingTeamDTO> getOne(
            @RequestParam Long tournamentId,
            @RequestParam Long teamId
    ) {
        ParticipatingTeam.ParticipatingTeamId id = new ParticipatingTeam.ParticipatingTeamId(tournamentId, teamId);
        return ResponseEntity.ok(
                ParticipatingTeamDTO.fromEntity(participatingTeamService.getOneById(id))
        );
    }

}
