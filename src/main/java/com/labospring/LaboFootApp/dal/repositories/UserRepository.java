package com.labospring.LaboFootApp.dal.repositories;

import com.labospring.LaboFootApp.dl.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>{

    @Query("select u from User u where u.username ilike :username")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("select count(u) > 0 from User u where u.username ilike :username")
    boolean existsByUsername(@Param("username") String username);

    @Query("select count(u) > 0 from User u where u.email ilike :email")
    boolean existsByUserEmail(@Param("email") String email);

    @Query("SELECT DISTINCT u FROM User u " +
            "LEFT JOIN FavoriteFootMatch ffm ON ffm.user = u " +
            "LEFT JOIN FavoriteTeam ft ON ft.user = u " +
            "WHERE (ffm.footMatch.id = :matchId OR ft.team.id IN :teamIds) " +
            "AND (ffm.notificationActivated = true OR ft.notificationActivated = true)")
    List<User> findUsersByFavoriteMatchOrTeamWithNotifications(@Param("matchId") Long matchId, @Param("teamIds") List<Long> matchIds);
}
