package com.labospring.LaboFootApp.pl.controller;

import com.labospring.LaboFootApp.bll.service.impl.FavoriteFootMatchServiceImpl;
import com.labospring.LaboFootApp.dl.entities.FavoriteFootMatch.FavoriteFootMatchId;
import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.pl.models.favoritefootmatch.FavoriteFootMatchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites/footmatch")
@RequiredArgsConstructor
public class FavoriteFootMatchController {

    private final FavoriteFootMatchServiceImpl notificationService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<Void> update(@RequestBody FavoriteFootMatchDTO favoriteFootMatchForm,
                                           Authentication authentication) {
        User user =(User) authentication.getPrincipal();
        notificationService.update(new FavoriteFootMatchId(user.getId(), favoriteFootMatchForm.footMatchId()), favoriteFootMatchForm.notification());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<FavoriteFootMatchDTO>> getAll(Authentication authentication) {
        User user =(User) authentication.getPrincipal();
        var list = notificationService
                .getAllByUser(user.getId())
                .stream()
                .map(FavoriteFootMatchDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(list);
    }
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{matchId:^\\d+}")
    public ResponseEntity<Void> remove( @PathVariable Long matchId,
                                           Authentication authentication) {
        User user =(User) authentication.getPrincipal();
        notificationService.remove(new FavoriteFootMatchId(user.getId(), matchId));
        return ResponseEntity.ok().build();
    }



}

