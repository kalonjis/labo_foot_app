package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.service.TeamService;
import com.labospring.LaboFootApp.bll.service.models.TeamBusiness;
import com.labospring.LaboFootApp.dal.repositories.TeamRepository;
import com.labospring.LaboFootApp.dl.entities.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public void deleteOne(Long id) {
        Team team = getOne(id);
        teamRepository.delete(team);
    }

    @Override
    public void updateOne(Long id, TeamBusiness entityBusiness) {
        Team team = getOne(id);
        //team.setPlayers(entityBusiness.players());
    }
}
