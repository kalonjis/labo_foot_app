package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.service.TournamentService;
import com.labospring.LaboFootApp.bll.service.models.TournamentBusiness;
import com.labospring.LaboFootApp.dal.repositories.TournamentRepository;
import com.labospring.LaboFootApp.dl.entities.Ranking;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO: GERER LA CREATION DE RANKING LORS DE LA CREATION DE TOURNAMENT AVEC GROUPE


@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;

    @Override
    public Long addOne(TournamentBusiness entityBusiness) {
        return tournamentRepository.save(entityBusiness.toEntity()).getId();
    }

    @Override
    public Tournament getOne(Long id) {
        return tournamentRepository.findById(id).orElseThrow(() -> new RuntimeException("No Tournament with ID " + id));
    }

    @Override
    public List<Tournament> getAll() {
        return tournamentRepository.findAll();
    }

    @Override
    public void deleteOne(Long id) {
        Tournament tournament = getOne(id);
        tournament.setRankingList(null);
        tournamentRepository.delete(tournament);

    }

    @Override
    public void updateOne(Long id, TournamentBusiness entityBusiness) {
        Tournament tournament = getOne(id);
        tournament.setTitle(entityBusiness.title());
        tournament.setStartDate(entityBusiness.startDate());
        tournament.setEndDate(entityBusiness.endDate());
        tournament.setPlaceName(entityBusiness.placeName());
        tournament.setAddress(entityBusiness.address());
        tournament.setTournamentType(entityBusiness.tournamentType());
        tournament.setTournamentStatus(entityBusiness.tournamentStatus());

        tournamentRepository.save(tournament);
    }


}
