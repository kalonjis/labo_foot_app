package com.labospring.LaboFootApp.dal.repositories;

import com.labospring.LaboFootApp.dl.entities.ParticipatingTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipatingTeamRepository extends JpaRepository<ParticipatingTeam, ParticipatingTeam.ParticipatingTeamId> {
}
