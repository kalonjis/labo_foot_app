package com.labospring.LaboFootApp.pl.controller;

import com.labospring.LaboFootApp.bll.service.TournamentService;
import com.labospring.LaboFootApp.pl.models.tournament.TournamentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
