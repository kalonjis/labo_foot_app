package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.exceptions.DoesntExistsException;
import com.labospring.LaboFootApp.bll.service.PlayerService;
import com.labospring.LaboFootApp.bll.service.TeamService;
import com.labospring.LaboFootApp.bll.service.models.matchactor.PlayerBusiness;
import com.labospring.LaboFootApp.bll.specification.PlayerSpecification;
import com.labospring.LaboFootApp.dal.repositories.PlayerRepository;
import com.labospring.LaboFootApp.dl.entities.Player;
import com.labospring.LaboFootApp.dl.entities.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamService teamService;

    @Override
    public Long addOne(PlayerBusiness playerBusiness) {
        Team team = teamService.getOne(playerBusiness.teamID());
        Player player = playerBusiness.toEntity();
        player.setTeam(team);

        return playerRepository.save(player).getId();
    }

    @Override
    public Player getOne(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new DoesntExistsException("No player with ID : " + id));
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
        if(playerBusiness.teamID() != null)
            player.setTeam( teamService.getOne(playerBusiness.teamID()) );
        else
            player.setTeam(null);

        player.setFirstname(playerBusiness.firstname());
        player.setLastname(playerBusiness.lastname());
        player.setFieldPosition(playerBusiness.fieldPosition());
        player.setTeamNumber(playerBusiness.teamNumber());
        player.setPlayerName(playerBusiness.playername());

        playerRepository.save(player);
    }

    public List<Player> getByCriteria(Player player){
        Specification<Player> playerSpecification = Specification.where(null);

       if(player.getPlayerName() != null && !player.getPlayerName().isEmpty()){
           playerSpecification = playerSpecification.and(PlayerSpecification.hasPlayerName(player.getPlayerName()));
       }
        if(player.getLastname() != null && !player.getLastname().isEmpty()){
            playerSpecification = playerSpecification.and(PlayerSpecification.hasLastname(player.getLastname()));
        }
        if(player.getFirstname() != null && !player.getFirstname().isEmpty()){
            playerSpecification = playerSpecification.and(PlayerSpecification.hasFirstname(player.getFirstname()));
        }
        if(player.getTeamNumber() != null){
            playerSpecification = playerSpecification.and(PlayerSpecification.hasTeamNumber(player.getTeamNumber()));
        }
        if(player.getFieldPosition() != null){
            playerSpecification = playerSpecification.and(PlayerSpecification.hasFieldPosition(player.getFieldPosition()));
        }

        return playerRepository.findAll(playerSpecification);
    }


}
