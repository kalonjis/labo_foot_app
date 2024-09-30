package com.labospring.LaboFootApp.pl.controller;

import com.labospring.LaboFootApp.bll.service.AccountService;
import com.labospring.LaboFootApp.bll.service.FootMatchService;
import com.labospring.LaboFootApp.bll.service.TeamService;
import com.labospring.LaboFootApp.bll.service.TournamentService;
import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.pl.models.footmatch.FootMatchListDetailsDTO;
import com.labospring.LaboFootApp.pl.models.team.TeamSmallDetailsDTO;
import com.labospring.LaboFootApp.pl.models.tournament.TournamentSmallDetailsDTO;
import com.labospring.LaboFootApp.pl.models.user.UserPasswordForm;
import com.labospring.LaboFootApp.pl.models.user.UserDTO;
import com.labospring.LaboFootApp.pl.models.user.UserEditForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;
    private final TournamentService tournamentService;
    private final TeamService teamService;
    private final FootMatchService footMatchService;

    @GetMapping("/info")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> get(){
        return ResponseEntity.ok( UserDTO.fromEntity( accountService.get() ));
    }


    @PutMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> update(@Valid @RequestBody UserEditForm userEditForm){
        accountService.update(userEditForm.toBusinessEntity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> remove(){
        accountService.delete();
        return ResponseEntity.ok().build();
    }


    @PutMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody UserPasswordForm passwordForm){
        accountService.changePassword(passwordForm.toBusinessEntity());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tournaments")
    @PreAuthorize("isAuthenticated")
    public ResponseEntity<List<TournamentSmallDetailsDTO>> getUserTournaments(Authentication authentication){
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(tournamentService.findAllByUser(user).stream().map(TournamentSmallDetailsDTO::fromEntity).toList());
    }

    @GetMapping("/teams")
    @PreAuthorize("isAuthenticated")
    public ResponseEntity<List<TeamSmallDetailsDTO>> getUserTeams(Authentication authentication){
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(teamService.findAllByUser(user).stream().map(TeamSmallDetailsDTO::fromEntity).toList());
    }

    @GetMapping("/matches-moderated")
    @PreAuthorize("isAuthenticated")
    public ResponseEntity<List<FootMatchListDetailsDTO>> getFootMatchModerated(Authentication authentication){
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(footMatchService.findAllByUser(user).stream().map(FootMatchListDetailsDTO::fromEntity).toList());
    }

}
