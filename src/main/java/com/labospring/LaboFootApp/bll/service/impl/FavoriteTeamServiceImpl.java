package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.exceptions.DoesntExistsException;
import com.labospring.LaboFootApp.bll.service.FavoriteTeamService;
import com.labospring.LaboFootApp.bll.service.TeamService;
import com.labospring.LaboFootApp.bll.service.UserService;
import com.labospring.LaboFootApp.dal.repositories.FavoriteTeamRepository;
import com.labospring.LaboFootApp.dl.entities.*;
import com.labospring.LaboFootApp.dl.entities.FavoriteTeam.FavoriteTeamId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteTeamServiceImpl implements FavoriteTeamService {

    private final FavoriteTeamRepository favoriteTeamRepository;
    private final TeamService teamService;
    private final UserService userService;

    @Override
    public void update(FavoriteTeamId id, boolean notificationActivated) {
        FavoriteTeam favoriteTeam;
        try{
            favoriteTeam = getOne(id);
            favoriteTeam.setNotificationActivated(notificationActivated);
        } catch (RuntimeException e) {
            User user = userService.getOne(id.getUserId());
            Team team = teamService.getOne(id.getTeamId());
            favoriteTeam = new FavoriteTeam(user, team, notificationActivated);
        }

        favoriteTeamRepository.save(favoriteTeam);
    }

    @Override
    public FavoriteTeam getOne(FavoriteTeamId id) {
        return favoriteTeamRepository.findById(id).orElseThrow(() -> new DoesntExistsException("No Favorite Team with ID : " + id.getTeamId()));

    }

    @Override
    public void remove(FavoriteTeamId id) {
        FavoriteTeam favoriteFootMatch = getOne(id);
        favoriteTeamRepository.delete(favoriteFootMatch);
    }

    @Override
    public List<FavoriteTeam> getAllByUser(Long userId) {
         User user = userService.getOne(userId);
        return favoriteTeamRepository.findAllByUser(user);
    }

    @Override
    public List<FavoriteTeam> getAllNotificationsByTeam(Team team) {
               return favoriteTeamRepository.findAllNotificationsByFootMatch(team);
    }
}
