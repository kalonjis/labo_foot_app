package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.exceptions.DoesntExistsException;
import com.labospring.LaboFootApp.bll.service.FavoriteFootMatchService;
import com.labospring.LaboFootApp.bll.service.FootMatchService;
import com.labospring.LaboFootApp.bll.service.UserService;
import com.labospring.LaboFootApp.dal.repositories.FavoriteFootMatchRepository;
import com.labospring.LaboFootApp.dl.entities.FavoriteFootMatch;
import com.labospring.LaboFootApp.dl.entities.FavoriteFootMatch.FavoriteFootMatchId;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteFootMatchServiceImpl implements FavoriteFootMatchService {

    private final FavoriteFootMatchRepository favoriteFootMatchRepository;
    private final UserService userService;
    private final FootMatchService footMatchService;

    public void update(FavoriteFootMatchId id, boolean notificationActivated) {
        FavoriteFootMatch favoriteFootMatch;
        try{
            favoriteFootMatch = getOne(id);
            favoriteFootMatch.setNotificationActivated(notificationActivated);
        } catch (RuntimeException e) {
            User user = userService.getOne(id.getUserId());
            FootMatch match = footMatchService.getOne(id.getFootMatchId());
            favoriteFootMatch = new FavoriteFootMatch(user, match, notificationActivated);
        }

        favoriteFootMatchRepository.save(favoriteFootMatch);
    }
    public FavoriteFootMatch getOne(FavoriteFootMatchId id){
        return favoriteFootMatchRepository.findById(id).orElseThrow(() -> new DoesntExistsException("No Favorite Match with ID : " + id.getFootMatchId()));
    }

    public void remove(FavoriteFootMatchId id){
        FavoriteFootMatch favoriteFootMatch = getOne(id);
        favoriteFootMatchRepository.delete(favoriteFootMatch);
    }

    public List<FavoriteFootMatch> getAllByUser(Long userId){
        User user = userService.getOne(userId);
        return favoriteFootMatchRepository.findAllByUser(user);
    }

    public List<FavoriteFootMatch> getAllNotificationsByFootMatch(FootMatch match){
        return favoriteFootMatchRepository.findAllNotificationsByFootMatch(match);
    }

}