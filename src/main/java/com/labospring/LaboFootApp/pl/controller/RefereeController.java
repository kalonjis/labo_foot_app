package com.labospring.LaboFootApp.pl.controller;

import com.labospring.LaboFootApp.bll.service.RefereeService;
import com.labospring.LaboFootApp.pl.models.referee.RefereeDTO;
import com.labospring.LaboFootApp.pl.models.referee.RefereeForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/referee")
public class RefereeController {
    private final RefereeService refereeService;

    @GetMapping("/{id:^\\d+}")
    public ResponseEntity<RefereeDTO> get(@PathVariable long id){
        return ResponseEntity.ok( RefereeDTO.fromEntity( refereeService.getOne(id) ));
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody RefereeForm RefereeForm){
        Long id = refereeService.addOne(RefereeForm.toRefereeBusiness());
        UriComponents uriComponents = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id);
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @GetMapping
    public ResponseEntity<List<RefereeDTO>> getAll(){
        return ResponseEntity.ok(refereeService.getAll().stream().map(RefereeDTO::fromEntity).toList());
    }

    @PutMapping("/{id:^\\d+}")
    public ResponseEntity<Void> update(@PathVariable long id,@Valid @RequestBody RefereeForm RefereeForm){
        refereeService.updateOne(id, RefereeForm.toRefereeBusiness());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id:^\\d+}")
    public ResponseEntity<Void> remove(@PathVariable long id){
        refereeService.deleteOne(id);
        return ResponseEntity.ok().build();
    }
}
