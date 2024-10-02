package com.labospring.LaboFootApp.pl.controller;

import com.labospring.LaboFootApp.bll.service.FavoriteTeamService;
import com.labospring.LaboFootApp.dl.entities.FavoriteTeam.FavoriteTeamId;
import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.pl.models.favoriteteam.FavoriteTeamDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/favorites/team")
@RequiredArgsConstructor
public class FavoriteTeamController {

    private final FavoriteTeamService favoriteTeamService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<Void> update(@RequestBody FavoriteTeamDTO favoriteFootMatchForm,
                                       Authentication authentication) {
        User user =(User) authentication.getPrincipal();
        favoriteTeamService.update(new FavoriteTeamId(user.getId(), favoriteFootMatchForm.teamId()), favoriteFootMatchForm.notification());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<FavoriteTeamDTO>> getAll(Authentication authentication) {
        User user =(User) authentication.getPrincipal();
        var list = favoriteTeamService.getAllByUser(user.getId())
                .stream()
                .map(FavoriteTeamDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(list);
    }
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{matchId:^\\d+}")
    public ResponseEntity<Void> remove( @PathVariable Long teamId,
                                        Authentication authentication) {
        User user =(User) authentication.getPrincipal();
        favoriteTeamService.remove(new FavoriteTeamId(user.getId(), teamId));
        return ResponseEntity.ok().build();
    }



}
