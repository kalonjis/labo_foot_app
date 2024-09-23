package com.labospring.LaboFootApp.pl.controller;

import com.labospring.LaboFootApp.bll.service.FootMatchService;
import com.labospring.LaboFootApp.pl.models.footmatch.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/footmatch")
public class FootMatchController {
    private final FootMatchService footMatchService;

    @GetMapping("/{id:^\\d+}")
    public ResponseEntity<FootMatchDetailsDTO> get(@PathVariable long id){
        return ResponseEntity.ok( FootMatchDetailsDTO.fromEntity( footMatchService.getOne(id) ));
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody FootMatchForm footMatchForm){
        Long id = footMatchService.addOne(footMatchForm.toFootMatchBusiness());
        UriComponents uriComponents = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id);
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @GetMapping
    public ResponseEntity<List<FootMatchListDetailsDTO>> getAll(){
        return ResponseEntity.ok(footMatchService.getAll().stream().map(FootMatchListDetailsDTO::fromEntity).toList());
    }

    @PutMapping("/{id:^\\d+}")
    public ResponseEntity<Void> update(@PathVariable long id,@Valid @RequestBody FootMatchEditForm footMatchForm){
        footMatchService.updateOne(id, footMatchForm.toFootMatchBusiness());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/score/{id:^\\d+}")
    public ResponseEntity<Void> updateScore(@PathVariable long id,@Valid @RequestBody ScoreFootMatchForm footMatchForm){
        footMatchService.changeScore(id, footMatchForm.toScoreBusiness());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/status/{id:^\\d+}")
    public ResponseEntity<Void> updateStatus(@PathVariable long id,@Valid @RequestBody StatusMatchForm statusMatchForm){
        footMatchService.changeStatus(id, statusMatchForm.matchStatus());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/mod/{id:^\\d+}")
    public ResponseEntity<Void> updateModerator(@PathVariable long id, @Valid @RequestBody FootMatchModeratorForm footMatchModeratorForm){
        footMatchService.changeModerator(id, footMatchModeratorForm.moderatorId());
        return ResponseEntity.ok().build();
    }

}
