package com.labospring.LaboFootApp.dal.repositories;

import com.labospring.LaboFootApp.dl.entities.FootMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FootMatchRepository extends JpaRepository<FootMatch, Long>, JpaSpecificationExecutor<FootMatch> {
}
