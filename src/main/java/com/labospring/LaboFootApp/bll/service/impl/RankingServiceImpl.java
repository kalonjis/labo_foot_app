package com.labospring.LaboFootApp.bll.service.impl;


import com.labospring.LaboFootApp.bll.service.RankingService;
import com.labospring.LaboFootApp.bll.service.models.RankingBusiness;
import com.labospring.LaboFootApp.dal.repositories.RankingRepository;
import com.labospring.LaboFootApp.dl.entities.Ranking;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

    private final RankingRepository rankingRepository;
    private final TournamentServiceImpl tournamentService;

    @Override
    public Long addOne(RankingBusiness entityBusiness) {
        Tournament t = tournamentService.getOne(entityBusiness.tournament_id());

        if(t.getTournamentType().getGroups() == null){
            throw new RuntimeException("Impossible to create ranking for tournament with id " +entityBusiness.tournament_id() +"  because it hasn't any group...");
        }

        Ranking r = new Ranking(t, entityBusiness.numGroup());
        return rankingRepository.save(r).getId();
    }

    @Override
    public Ranking getOne(Long id) {
        return rankingRepository.findById(id).orElseThrow(() -> new RuntimeException("No Ranking with ID " + id));
    }

    @Override
    public List<Ranking> getAll() {
        return rankingRepository.findAll();
    }

    @Override
    public void deleteOne(Long id) {

    }

    @Override
    public void updateOne(Long id, RankingBusiness entityBusiness) {

    }
}
