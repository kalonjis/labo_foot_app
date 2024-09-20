package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.service.PlayerService;
import com.labospring.LaboFootApp.bll.service.models.PlayerBusiness;
import com.labospring.LaboFootApp.dal.repositories.PlayerRepository;
import com.labospring.LaboFootApp.dal.repositories.TeamRepository;
import com.labospring.LaboFootApp.dl.entities.Player;
import com.labospring.LaboFootApp.dl.entities.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    //TODO teamService

    @Override
    public Long addOne(PlayerBusiness playerBusiness) {
        Team team = null;
        if(playerBusiness.teamID() != null)
            team = teamRepository.findById(playerBusiness.teamID()).orElseThrow(() -> new RuntimeException());
        Player player = playerBusiness.toEntity();
        player.changeTeam(team);

        return playerRepository.save(player).getId();
    }

    @Override
    public Player getOne(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new RuntimeException("No player with ID : " + id));
    }

    @Override
    public List<Player> getAll() {
        return playerRepository.findAll();
    }

    @Override
    public void deleteOne(Long id) {
        Player player = getOne(id);
        playerRepository.delete(player);
    }

    @Override
    public void updateOne(Long id, PlayerBusiness playerBusiness) {
        Player player = getOne(id);
        player.setFirstname(playerBusiness.firstname());
        player.setLastname(playerBusiness.lastname());
        player.setFieldPosition(playerBusiness.fieldPosition());
        player.setTeamNumber(playerBusiness.teamNumber());
        player.setPlayerName(playerBusiness.playername());

        playerRepository.save(player);
    }
}
