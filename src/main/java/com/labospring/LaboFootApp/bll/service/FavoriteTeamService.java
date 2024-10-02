package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.dl.entities.FavoriteTeam;
import com.labospring.LaboFootApp.dl.entities.Team;

import java.util.List;

public interface FavoriteTeamService {
    void update(FavoriteTeam.FavoriteTeamId id, boolean notificationActivated);
    FavoriteTeam getOne(FavoriteTeam.FavoriteTeamId id);
    void remove(FavoriteTeam.FavoriteTeamId id);
    List<FavoriteTeam> getAllByUser(Long userId);
    List<FavoriteTeam> getAllNotificationsByTeam(Team team);

}
