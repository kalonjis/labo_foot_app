package com.labospring.LaboFootApp.pl.controller;


import com.labospring.LaboFootApp.bll.service.RankingService;
import com.labospring.LaboFootApp.pl.models.ranking.RankingDTO;
import com.labospring.LaboFootApp.pl.models.ranking.RankingEditForm;
import com.labospring.LaboFootApp.pl.models.ranking.RankingForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("by-group")
    public ResponseEntity<List<RankingDTO>> getByGroup(
            @RequestParam Long tournamentId,
            @RequestParam int numGroup
            ){
        return ResponseEntity.ok(
                rankingService.getAllByTournamentIdAndNumGroup(tournamentId, numGroup)
                        .stream()
                        .map(RankingDTO::fromEntity)
                        .toList()
        );
    }

//    @PostMapping
//    public ResponseEntity<Void> create(@Valid @RequestBody RankingForm rankingform){
//        Long id = rankingService.addOne(rankingform.toRankingBusiness());
//        UriComponents uriComponents = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id);
//        return ResponseEntity.created(uriComponents.toUri()).build();
//    }

    @DeleteMapping("/{id:^\\d+}")
    @PreAuthorize("isAuthenticated() && (@accessControlService.isUserRanking(principal, #id) || hasAuthority('ADMIN'))")
    public ResponseEntity<Void> remove(@PathVariable long id){
        rankingService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id:^\\d+}")
    @PreAuthorize("isAuthenticated() && (@accessControlService.isUserRanking(principal, #id) || hasAuthority('ADMIN'))")
    public ResponseEntity<Void> update(@PathVariable long id, @Valid @RequestBody RankingEditForm editForm){
        rankingService.update(id, editForm.toRankingEditBusiness());
        return ResponseEntity.ok().build();
    }

}
