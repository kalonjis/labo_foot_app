package com.labospring.LaboFootApp.pl.controller;

import com.labospring.LaboFootApp.bll.service.TournamentService;
import com.labospring.LaboFootApp.pl.models.tournament.TournamentDTO;
import com.labospring.LaboFootApp.pl.models.tournament.TournamentForm;
import com.labospring.LaboFootApp.pl.models.tournament.TournamentStatusForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tournament")
public class TournamentController {

    private final TournamentService tournamentService;

    @GetMapping
    public ResponseEntity<List<TournamentDTO>> getAll(){
        return ResponseEntity.ok(
                tournamentService.getAll().stream()
                        .map(TournamentDTO::fromEntity)
                        .toList()
        );
    }

    @GetMapping("/{id:^\\d+}")
    public ResponseEntity<TournamentDTO> get(@PathVariable long id){
        return ResponseEntity.ok(
                TournamentDTO.fromEntity(tournamentService.getOne(id))
        );
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody TournamentForm tournamentForm){
        Long id = tournamentService.addOne(tournamentForm.toTournamentBusiness());
        UriComponents uriComponents = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id);
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @PutMapping("/{id:^\\d+}")
    //@PreAuthorize("isAuthenticated && (@accessControlService.isOrganizerTournament(principal, #id) || hasAuthority('ADMIN'))")
    public ResponseEntity<Void> update(@PathVariable long id, @Valid @RequestBody TournamentForm tournamentForm){
        tournamentService.updateOne(id, tournamentForm.toTournamentBusiness());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/status/{id:^\\d+}")
    //@PreAuthorize("isAuthenticated && (@accessControlService.isOrganizerTournament(principal, #id) || hasAuthority('ADMIN'))")
    public ResponseEntity<Void> updateStatus(@PathVariable long id, @Valid @RequestBody TournamentStatusForm tournamentStatusForm ){
        tournamentService.updateStatus(id, tournamentStatusForm.tournamentStatus());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id:^\\d+}")
    public ResponseEntity<Void> remove(@PathVariable long id){
        tournamentService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

}
