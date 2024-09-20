package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.service.PlayerService;
import com.labospring.LaboFootApp.bll.service.models.PlayerBusiness;
import com.labospring.LaboFootApp.dal.repositories.PlayerRepository;
import com.labospring.LaboFootApp.dl.entities.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    @Override
    public Long addOne(PlayerBusiness entityBusiness) {
        return playerRepository.save(entityBusiness.toEntity()).getId();
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
