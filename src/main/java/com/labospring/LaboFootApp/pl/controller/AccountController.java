package com.labospring.LaboFootApp.pl.controller;

import com.labospring.LaboFootApp.bll.service.AccountService;
import com.labospring.LaboFootApp.pl.models.user.UserPasswordForm;
import com.labospring.LaboFootApp.pl.models.user.UserDTO;
import com.labospring.LaboFootApp.pl.models.user.UserEditForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

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

}
