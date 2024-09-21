package com.labospring.LaboFootApp.pl.controller;

import com.labospring.LaboFootApp.bll.service.TeamService;
import com.labospring.LaboFootApp.pl.models.team.TeamDTO;
import com.labospring.LaboFootApp.pl.models.team.TeamEditForm;
import com.labospring.LaboFootApp.pl.models.team.TeamForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class TeamController {
    private final TeamService teamService;

    @GetMapping("/{id:^\\d+}")
    public ResponseEntity<TeamDTO> get(@PathVariable long id){
        return ResponseEntity.ok( TeamDTO.fromEntity( teamService.getOne(id) ));
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody TeamForm teamForm){
        Long id = teamService.addOne(teamForm.toTeamBusiness());
        UriComponents uriComponents = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id);
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAll(){
        return ResponseEntity.ok(teamService.getAll().stream().map(TeamDTO::fromEntity).toList());
    }

    @PutMapping("/{id:^\\d+}")
    public ResponseEntity<Void> update(@PathVariable long id,@Valid  @RequestBody TeamEditForm teamForm){
        teamService.updateOne(id, teamForm.toTeamBusiness());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id:^\\d+}")
    public ResponseEntity<Void> remove(@PathVariable long id){
        teamService.deleteOne(id);
        return ResponseEntity.ok().build();
    }
}
