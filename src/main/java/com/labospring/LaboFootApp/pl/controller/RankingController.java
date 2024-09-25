package com.labospring.LaboFootApp.pl.controller;


import com.labospring.LaboFootApp.bll.service.RankingService;
import com.labospring.LaboFootApp.pl.models.ranking.RankingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ranking")
public class RankingController {

    private final RankingService rankingService;

    @GetMapping
    public ResponseEntity<List<RankingDTO>> getAll(){
        return ResponseEntity.ok(rankingService
                .getAll()
                .stream()
                .map(RankingDTO::fromEntity)
                .toList()
        );
    }


    @GetMapping("/{id:^\\d+}")
    public ResponseEntity<RankingDTO> get(@PathVariable long id){
        return ResponseEntity.ok(
                RankingDTO.fromEntity(rankingService.getOne(id))
        );
    }
}
