package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.bll.service.models.TournamentBusiness;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.enums.TournamentStatus;

public interface TournamentService extends BaseService<Long, Tournament, TournamentBusiness>, BaseByUser<Tournament>{
    void updateStatus(Long id, TournamentStatus tournamentStatus);
    boolean tournamentExists(Long id);

}
