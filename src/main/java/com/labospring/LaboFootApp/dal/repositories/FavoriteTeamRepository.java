package com.labospring.LaboFootApp.dal.repositories;

import com.labospring.LaboFootApp.dl.entities.FavoriteTeam;
import com.labospring.LaboFootApp.dl.entities.FavoriteTeam.FavoriteTeamId;
import com.labospring.LaboFootApp.dl.entities.Team;
import com.labospring.LaboFootApp.dl.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoriteTeamRepository extends JpaRepository<FavoriteTeam, FavoriteTeamId> {
    @Query("select f from FavoriteTeam f where f.user = :user")
    List<FavoriteTeam> findAllByUser(User user);

    @Query("select f from FavoriteTeam f where f.team = :team and f.notificationActivated = true")
    List<FavoriteTeam> findAllNotificationsByFootMatch(Team team);

}
