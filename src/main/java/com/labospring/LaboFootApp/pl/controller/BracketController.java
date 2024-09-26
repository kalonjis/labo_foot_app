package com.labospring.LaboFootApp.pl.controller;

import com.labospring.LaboFootApp.bll.service.BracketService;
import com.labospring.LaboFootApp.pl.models.BracketDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bracket")
public class BracketController {
    private final BracketService bracketService;

    @GetMapping
    public ResponseEntity<List<BracketDTO>> get(@RequestParam long tournamentId){
        return ResponseEntity.ok( bracketService.getListByTournament(tournamentId).stream().map(BracketDTO::fromEntity).toList() );
    }
}
