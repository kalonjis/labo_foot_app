package com.labospring.LaboFootApp.pl.controller;


import com.labospring.LaboFootApp.bll.service.ParticipatingTeamService;
import com.labospring.LaboFootApp.dl.entities.ParticipatingTeam;
import com.labospring.LaboFootApp.pl.models.participatingTeam.ParticipatingTeamDTO;
import com.labospring.LaboFootApp.pl.models.participatingTeam.ParticipatingTeamForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

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

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody ParticipatingTeamForm form){
        ParticipatingTeam.ParticipatingTeamId id = participatingTeamService.createOne(form.toParticipatingTeamBusiness());
        UriComponents uriComponents = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id);
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> remove(
            @RequestParam Long tournamentId,
            @RequestParam Long teamId
    ){
        ParticipatingTeam.ParticipatingTeamId id = new ParticipatingTeam.ParticipatingTeamId(tournamentId, teamId);
        participatingTeamService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
