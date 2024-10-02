package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.exceptions.DoesntExistsException;
import com.labospring.LaboFootApp.bll.service.TeamService;
import com.labospring.LaboFootApp.bll.service.models.team.TeamBusiness;
import com.labospring.LaboFootApp.dal.repositories.TeamRepository;
import com.labospring.LaboFootApp.dl.entities.Team;
import com.labospring.LaboFootApp.dl.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    @Override
    public Long addOne(TeamBusiness entityBusiness) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Team team = entityBusiness.toEntity();
        team.setCreator(user);
        return teamRepository.save(team).getId();
    }

    @Override
    public Team getOne(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new DoesntExistsException("No Team with ID " + id));
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
        team.setName(teamUpdated.getName());
        if(team.getCoach() != null && entityBusiness.coach() != null){
            team.getCoach().setLastname(teamUpdated.getCoach().getLastname());
            team.getCoach().setFirstname(teamUpdated.getCoach().getFirstname());
        }

        teamRepository.save(team);
    }

    @Override
    public List<Team> findAllByUser(User user) {
        return teamRepository.findAllByUser(user);
    }
}
