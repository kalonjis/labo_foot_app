package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.service.TournamentService;
import com.labospring.LaboFootApp.bll.service.models.TournamentBusiness;
import com.labospring.LaboFootApp.dal.repositories.TournamentRepository;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;

    @Override
    public Long addOne(TournamentBusiness entityBusiness) {
        return 0L;
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
    public void deleteOne(Long aLong) {

    }

    @Override
    public void updateOne(Long aLong, TournamentBusiness entityBusiness) {

    }
}
