package com.labospring.LaboFootApp.pl.controller;


import com.labospring.LaboFootApp.bll.service.ParticipatingTeamService;
import com.labospring.LaboFootApp.dl.entities.ParticipatingTeam;
import com.labospring.LaboFootApp.pl.models.participatingTeam.ParticipatingTeamDTO;
import com.labospring.LaboFootApp.pl.models.participatingTeam.ParticipatingTeamForm;
import com.labospring.LaboFootApp.pl.models.participatingTeam.ParticipatingTeamStatusForm;
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

    @GetMapping("/by-tournament/{id:^\\d+}")
    public ResponseEntity<List<ParticipatingTeamDTO>> getAllTeamsByTournament(@PathVariable long id){
        return ResponseEntity.ok(
                participatingTeamService.getAllTeamsByTournament(id)
                        .stream()
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

    @PutMapping("/status/")
    // Route reservée à l'organisateur/modérateur
    public ResponseEntity<Void> updateStatus(
            @RequestParam Long tournamentId,
            @RequestParam Long teamId,
            @Valid @RequestBody ParticipatingTeamStatusForm form
    ){
        ParticipatingTeam.ParticipatingTeamId id = new ParticipatingTeam.ParticipatingTeamId(tournamentId, teamId);
        participatingTeamService.changeStatus(id, form.subscriptionStatus());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/status-canceled/")
    // Route reservée au user responsable de sa Team
    public ResponseEntity<Void> updateStatusToCanceled(
            @RequestParam Long tournamentId,
            @RequestParam Long teamId
    ){
        ParticipatingTeam.ParticipatingTeamId id = new ParticipatingTeam.ParticipatingTeamId(tournamentId, teamId);
        participatingTeamService.changeStatusToCanceled(id);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/dipatching-teams")
    public ResponseEntity<Void> dispatchTeamsInGroups(@RequestParam Long tournamentId){
        participatingTeamService.dispatchTeamsToGroups(tournamentId);
        return ResponseEntity.ok().build();
    }
}
