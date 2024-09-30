package com.labospring.LaboFootApp.pl.controller;

import com.labospring.LaboFootApp.bll.service.FootMatchService;
import com.labospring.LaboFootApp.bll.service.models.FootMatchSpecificationDTO;
import com.labospring.LaboFootApp.dl.entities.Coach;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.pl.models.coach.CoachDTO;
import com.labospring.LaboFootApp.pl.models.coach.CoachForm;
import com.labospring.LaboFootApp.pl.models.footmatch.*;
import com.labospring.LaboFootApp.pl.models.footmatch.form.*;
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
@RequestMapping("/api/footmatch")
public class FootMatchController {
    private final FootMatchService footMatchService;

    @GetMapping("/{id:^\\d+}")
    public ResponseEntity<FootMatchDetailsDTO> get(@PathVariable long id){
        return ResponseEntity.ok( FootMatchDetailsDTO.fromEntity( footMatchService.getOne(id) ));
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody FootMatchPostForm footMatchForm){
        Long id = footMatchService.addOne(footMatchForm.toFootMatchBusiness());
        UriComponents uriComponents = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id);
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @GetMapping
    public ResponseEntity<List<FootMatchListDetailsDTO>> getAll(){
        return ResponseEntity.ok(footMatchService.getAll().stream().map(FootMatchListDetailsDTO::fromEntity).toList());
    }

    @DeleteMapping("/{id:^\\d+}")
    public ResponseEntity<Void> remove(@PathVariable long id){
        footMatchService.deleteOne(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id:^\\d+}")
    public ResponseEntity<Void> update(@PathVariable long id,@Valid @RequestBody FootMatchEditForm footMatchForm){
        footMatchService.updateOne(id, footMatchForm.toFootMatchBusiness());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/score/{id:^\\d+}")
    //@PreAuthorize("isAuthenticated() && (@accessControlService.isOrganizerMatch(principal, #id) || @accessControlService.isModeratorMatch(principal, #id) || hasAuthority('ADMIN')))")
    public ResponseEntity<Void> updateScore(@PathVariable long id,@Valid @RequestBody FootMatchScoreForm footMatchForm){
        footMatchService.changeScore(id, footMatchForm.toScoreBusiness());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/status/{id:^\\d+}")
    //@PreAuthorize("isAuthenticated() && (@accessControlService.isOrganizerMatch(principal, #id) || @accessControlService.isModeratorMatch(principal, #id)  || hasAuthority('ADMIN')))")
    public ResponseEntity<Void> updateStatus(@PathVariable long id,@Valid @RequestBody FootMatchStatusForm statusMatchForm){
        footMatchService.changeStatus(id, statusMatchForm.matchStatus());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/mod/{id:^\\d+}")
    //@PreAuthorize("isAuthenticated() && @accessControlService.isOrganizerMatch(principal, #id)  || hasAuthority('ADMIN'))")
    public ResponseEntity<Void> updateModerator(@PathVariable long id, @Valid @RequestBody FootMatchModeratorForm footMatchModeratorForm){
        footMatchService.changeModerator(id, footMatchModeratorForm.moderatorId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<FootMatchListDetailsDTO>> search(@RequestBody FootMatchSpecificationDTO footDTO){
        List<FootMatch> footMatches = footMatchService.getByCriteria(footDTO);

        return ResponseEntity.ok(footMatches.stream().map(FootMatchListDetailsDTO::fromEntity).toList());

    }

}
