package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.dal.repositories.FavoriteFootMatchRepository;
import com.labospring.LaboFootApp.dl.entities.FavoriteFootMatch;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final FavoriteFootMatchRepository favoriteFootMatchRepository;
    private final UserService userService;
    private final FootMatchService footMatchService;

    // Subscribe to notifications for a foot match
    public void subscribe(Long userId, Long matchId) {
        User user = userService.getOne(userId);
        FootMatch match = footMatchService.getOne(matchId);

        FavoriteFootMatch favoriteFootMatch = new FavoriteFootMatch(user, match);
        favoriteFootMatch.setNotificationActivated(true);
        favoriteFootMatchRepository.save(favoriteFootMatch);

    }

    // Unsubscribe from notifications for a foot match
    public void unsubscribe(Long userId, Long matchId) {
        User user = userService.getOne(userId);
        FootMatch match = footMatchService.getOne(matchId);

         FavoriteFootMatch favoriteFootMatch= favoriteFootMatchRepository.findByUserAndFootMatch(user, match).orElseThrow();
         favoriteFootMatch.setNotificationActivated(false);

         favoriteFootMatchRepository.save(favoriteFootMatch);
    }
}