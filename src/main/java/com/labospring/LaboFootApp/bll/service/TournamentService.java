package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.bll.service.models.TournamentBusiness;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.enums.TournamentStatus;

import java.util.Collection;
import java.util.List;

public interface TournamentService extends BaseService<Long, Tournament, TournamentBusiness>, BaseByUser<Tournament>{
    void updateStatus(Long id, TournamentStatus tournamentStatus);
    boolean tournamentExists(Long id);
    List<Tournament> getAllFromOrganizer();
}
