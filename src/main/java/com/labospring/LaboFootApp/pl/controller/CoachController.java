package com.labospring.LaboFootApp.pl.controller;

import com.labospring.LaboFootApp.bll.service.CoachService;
import com.labospring.LaboFootApp.dl.entities.Coach;
import com.labospring.LaboFootApp.dl.entities.Player;
import com.labospring.LaboFootApp.pl.models.coach.CoachDTO;
import com.labospring.LaboFootApp.pl.models.coach.CoachForm;
import com.labospring.LaboFootApp.pl.models.player.PlayerDTO;
import com.labospring.LaboFootApp.pl.models.player.PlayerSpecificationForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coach")
public class CoachController {
    private final CoachService coachService;

    @GetMapping("/{id:^\\d+}")
    public ResponseEntity<CoachDTO> get(@PathVariable long id){
        return ResponseEntity.ok( CoachDTO.fromEntity( coachService.getOne(id) ));
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CoachForm coachForm){
        Long id = coachService.addOne(coachForm.toCoachBusiness());
        UriComponents uriComponents = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id);
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @GetMapping
    public ResponseEntity<List<CoachDTO>> getAll(){
        return ResponseEntity.ok(coachService.getAll().stream().map(CoachDTO::fromEntity).toList());
    }

    @PutMapping("/{id:^\\d+}")
    public ResponseEntity<Void> update(@PathVariable long id,@Valid @RequestBody CoachForm coachForm){
        coachService.updateOne(id, coachForm.toCoachBusiness());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id:^\\d+}")
    public ResponseEntity<Void> remove(@PathVariable long id){
        coachService.deleteOne(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<CoachDTO>> search(@RequestBody CoachForm coachForm){
        List<Coach> coaches = coachService.getByCriteria(coachForm.toCoachBusiness().toEntity());

        return ResponseEntity.ok(coaches.stream().map(CoachDTO::fromEntity).toList());

    }
}
