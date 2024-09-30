package com.labospring.LaboFootApp.pl.controller;

import com.labospring.LaboFootApp.bll.service.PlayerService;
import com.labospring.LaboFootApp.dl.entities.Player;
import com.labospring.LaboFootApp.pl.models.player.PlayerDTO;
import com.labospring.LaboFootApp.pl.models.player.PlayerForm;
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
@RequestMapping("/api/player")
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping("/{id:^\\d+}")
    public ResponseEntity<PlayerDTO> get(@PathVariable long id){
        return ResponseEntity.ok( PlayerDTO.fromEntity( playerService.getOne(id) ));
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody PlayerForm playerForm){
        Long id = playerService.addOne(playerForm.toPlayerBusiness());
        UriComponents uriComponents = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id);
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getAll(){
        return ResponseEntity.ok(playerService.getAll().stream().map(PlayerDTO::fromEntity).toList());
    }

    @PutMapping("/{id:^\\d+}")
    public ResponseEntity<Void> update(@PathVariable long id,@Valid @RequestBody PlayerForm playerForm){
        playerService.updateOne(id, playerForm.toPlayerBusiness());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id:^\\d+}")
    public ResponseEntity<Void> remove(@PathVariable long id){
        playerService.deleteOne(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<PlayerDTO>> search(@RequestBody PlayerSpecificationForm playerForm){
        List<Player> players = playerService.getByCriteria(playerForm.toEntity());

        return ResponseEntity.ok(players.stream().map(PlayerDTO::fromEntity).toList());

    }
}
