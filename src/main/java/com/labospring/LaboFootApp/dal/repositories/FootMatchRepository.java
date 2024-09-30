package com.labospring.LaboFootApp.dal.repositories;

import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FootMatchRepository extends JpaRepository<FootMatch, Long> {
    @Query("select f from FootMatch f where f.userModerator = :user or f.tournament.creator = :user")
    List<FootMatch> findAllByUser(User user);
}
