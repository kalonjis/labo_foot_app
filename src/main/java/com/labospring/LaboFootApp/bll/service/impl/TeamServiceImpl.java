package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.service.TeamService;
import com.labospring.LaboFootApp.bll.service.models.TeamBusiness;
import com.labospring.LaboFootApp.dal.repositories.TeamRepository;
import com.labospring.LaboFootApp.dl.entities.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    @Override
    public Long addOne(TeamBusiness entityBusiness) {
        return teamRepository.save(entityBusiness.toEntity()).getId();
    }

    @Override
    public Team getOne(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new RuntimeException("No Team with ID " + id));
    }

    @Override
    public List<Team> getAll() {
        return teamRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteOne(Long id) {
        Team team = getOne(id);

        team.removeAllPlayers();
        teamRepository.delete(team);
    }

    @Override
    public void updateOne(Long id, TeamBusiness entityBusiness) {
        Team team = getOne(id);
        Team teamUpdated = entityBusiness.toEntity();
        team.setPlayers(teamUpdated.getPlayers());
        team.setCoach(teamUpdated.getCoach());
        team.setName(teamUpdated.getName());

        teamRepository.save(team);
    }
}
