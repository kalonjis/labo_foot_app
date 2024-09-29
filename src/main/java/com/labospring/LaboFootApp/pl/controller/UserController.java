package com.labospring.LaboFootApp.pl.controller;

import com.labospring.LaboFootApp.bll.service.UserService;
import com.labospring.LaboFootApp.pl.models.user.UserSmallDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/search")
    public ResponseEntity<List<UserSmallDTO>> getAllByUsername(@RequestParam String username){
        return ResponseEntity.ok(userService.findAllByUsername(username).stream().map(UserSmallDTO::fromEntity).toList());
    }
}
