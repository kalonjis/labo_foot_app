package com.labospring.LaboFootApp.dal.repositories;

import com.labospring.LaboFootApp.dl.entities.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CoachRepository extends JpaRepository<Coach, Long>, JpaSpecificationExecutor<Coach> {
}
