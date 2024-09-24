package com.labospring.LaboFootApp.pl.controller;

import com.labospring.LaboFootApp.bll.service.TournamentService;
import com.labospring.LaboFootApp.pl.models.tournament.TournamentDTO;
import com.labospring.LaboFootApp.pl.models.tournament.TournamentForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

}
