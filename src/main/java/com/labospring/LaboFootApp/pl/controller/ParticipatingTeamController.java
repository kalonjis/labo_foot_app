package com.labospring.LaboFootApp.pl.controller;


import com.labospring.LaboFootApp.bll.security.impl.AccessControlService;
import com.labospring.LaboFootApp.bll.service.ParticipatingTeamService;
import com.labospring.LaboFootApp.dl.entities.ParticipatingTeam;
import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.dl.enums.Role;
import com.labospring.LaboFootApp.dl.enums.SubscriptionStatus;
import com.labospring.LaboFootApp.pl.models.participatingTeam.ParticipatingTeamDTO;
import com.labospring.LaboFootApp.pl.models.participatingTeam.ParticipatingTeamForm;
import com.labospring.LaboFootApp.pl.models.participatingTeam.ParticipatingTeamStatusForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/participatingteam")
public class ParticipatingTeamController {

    private final ParticipatingTeamService participatingTeamService;
    private final AccessControlService accessControlService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ParticipatingTeamDTO>> getAll(){
        return ResponseEntity.ok(
                participatingTeamService.getAll().stream()
                        .map(ParticipatingTeamDTO::fromEntity)
                        .toList()
        );
    }

    @GetMapping("/by-tournament/{id:^\\d+}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ParticipatingTeamDTO>> getAllTeamsByTournament(@PathVariable long id){
        return ResponseEntity.ok(
                participatingTeamService.getAllTeamsByTournament(id)
                        .stream()
                        .map(ParticipatingTeamDTO::fromEntity)
                        .toList()
        );
    }

    @GetMapping("/by-tournament-and-status")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ParticipatingTeamDTO>> getAllByTournamentAndStatus(
            @RequestParam long tournamentId,
            @RequestParam SubscriptionStatus status
            ){
        return ResponseEntity.ok(participatingTeamService.getAllByTournamentAndStatus(tournamentId, status)
                .stream()
                .map(ParticipatingTeamDTO::fromEntity)
                .toList()
        );
    }

    @GetMapping("/by-ids")
    @PreAuthorize("isAuthenticated()")
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
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> create(@Valid @RequestBody ParticipatingTeamForm form, Authentication authentication){
        User user =(User) authentication.getPrincipal();
        if(!accessControlService.isUserTeam(user, form.teamId()) && !accessControlService.isOrganizerTournament(user, form.tournamentId()) &&
        !user.getRole().equals(Role.ADMIN))
            throw new AccessDeniedException("Access Denied");

        ParticipatingTeam.ParticipatingTeamId id = participatingTeamService.createOne(form.toParticipatingTeamBusiness());
        UriComponents uriComponents = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id);
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @DeleteMapping
    @PreAuthorize("isAuthenticated() && (@accessControlService.isOrganizerTournament(principal, #tournamentId) || " +
            "@accessControlService.isUserTeam(principal,#teamId) || hasAuthority('ADMIN'))")
    public ResponseEntity<Void> remove(
            @RequestParam Long tournamentId,
            @RequestParam Long teamId
    ){
        ParticipatingTeam.ParticipatingTeamId id = new ParticipatingTeam.ParticipatingTeamId(tournamentId, teamId);
        participatingTeamService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/status/")
    @PreAuthorize("isAuthenticated() && (@accessControlService.isOrganizerTournament(principal, #tournamentId) || hasAuthority('ADMIN'))")
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
    @PreAuthorize("isAuthenticated() && (@accessControlService.isUserTeam(principal,#teamId) || hasAuthority('ADMIN')) ")
    public ResponseEntity<Void> updateStatusToCanceled(
            @RequestParam Long tournamentId,
            @RequestParam Long teamId
    ){
        ParticipatingTeam.ParticipatingTeamId id = new ParticipatingTeam.ParticipatingTeamId(tournamentId, teamId);
        participatingTeamService.changeStatusToCanceled(id);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/dipatching-teams-to-groups")
    @PreAuthorize("isAuthenticated() && (@accessControlService.isOrganizerTournament(principal, #tournamentId) || hasAuthority('ADMIN'))")
    public ResponseEntity<Void> dispatchTeamsInGroups(@RequestParam Long tournamentId){
        participatingTeamService.dispatchTeamsToGroups(tournamentId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/dipatching-teams-to-bracket")
    @PreAuthorize("isAuthenticated() && (@accessControlService.isOrganizerTournament(principal, #tournamentId) || hasAuthority('ADMIN'))")
    public ResponseEntity<Void> dispatchTeamsInBracket(@RequestParam Long tournamentId){
        participatingTeamService.dispatchTeamsToBrackets(tournamentId);
        return ResponseEntity.ok().build();
    }
}
