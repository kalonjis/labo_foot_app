package com.labospring.LaboFootApp.pl.controller;

import com.labospring.LaboFootApp.bll.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class FavoriteFootMatchController {

    private final NotificationService notificationService;

    @PostMapping("/subscribe")
    public ResponseEntity<Void> subscribe(@RequestParam Long userId, @RequestParam Long matchId) {
        notificationService.subscribe(userId, matchId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<Void> unsubscribe(@RequestParam Long userId, @RequestParam Long matchId) {
        notificationService.unsubscribe(userId, matchId);
        return ResponseEntity.ok().build();
    }
}

