package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.dl.entities.FavoriteFootMatch;
import com.labospring.LaboFootApp.dl.entities.FootMatch;

import java.util.List;

public interface FavoriteFootMatchService {
    void update(FavoriteFootMatch.FavoriteFootMatchId id, boolean notificationActivated);
    FavoriteFootMatch getOne(FavoriteFootMatch.FavoriteFootMatchId id);
    void remove(FavoriteFootMatch.FavoriteFootMatchId id);
    List<FavoriteFootMatch> getAllByUser(Long userId);
    List<FavoriteFootMatch> getAllNotificationsByFootMatch(FootMatch footMatch);

}
