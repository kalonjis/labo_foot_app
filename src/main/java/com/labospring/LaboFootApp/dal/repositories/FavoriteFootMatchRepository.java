package com.labospring.LaboFootApp.dal.repositories;

import com.labospring.LaboFootApp.dl.entities.FavoriteFootMatch;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteFootMatchRepository extends JpaRepository<FavoriteFootMatch, FavoriteFootMatch.FavoriteFootMatchId> {

    @Query("select f from FavoriteFootMatch f where f.footMatch = :footMatch and f.notificationActivated = true")
    List<FavoriteFootMatch> findAllNotificationsByFootMatch(FootMatch footMatch);

    @Query("select f from FavoriteFootMatch f where f.footMatch = :footMatch and f.user = :user")
    Optional<FavoriteFootMatch> findByUserAndFootMatch(User user, FootMatch footMatch);
}
