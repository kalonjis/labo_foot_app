package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.bll.service.models.matchactor.PlayerBusiness;
import com.labospring.LaboFootApp.dl.entities.Player;

import java.util.List;

public interface PlayerService extends BaseService<Long, Player, PlayerBusiness> {
    List<Player> getByCriteria(Player player);
}
