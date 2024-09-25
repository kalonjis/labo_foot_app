package com.labospring.LaboFootApp.pl.controller;


import com.labospring.LaboFootApp.bll.service.RankingService;
import com.labospring.LaboFootApp.pl.models.ranking.RankingDTO;
import com.labospring.LaboFootApp.pl.models.ranking.RankingEditForm;
import com.labospring.LaboFootApp.pl.models.ranking.RankingForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

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

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody RankingForm rankingform){
        Long id = rankingService.addOne(rankingform.toRankingBusiness());
        UriComponents uriComponents = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id);
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @DeleteMapping("/{id:^\\d+}")
    public ResponseEntity<Void> remove(@PathVariable long id){
        rankingService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id:^\\d+}")
    public ResponseEntity<Void> update(@PathVariable long id, @Valid @RequestBody RankingEditForm editForm){
        rankingService.update(id, editForm.toRankingEditBusiness());
        return ResponseEntity.ok().build();
    }

}
