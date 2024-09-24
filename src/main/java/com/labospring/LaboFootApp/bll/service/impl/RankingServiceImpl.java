package com.labospring.LaboFootApp.bll.service.impl;


import com.labospring.LaboFootApp.bll.service.RankingService;
import com.labospring.LaboFootApp.bll.service.models.RankingtBusiness;
import com.labospring.LaboFootApp.dal.repositories.RankingRepository;
import com.labospring.LaboFootApp.dl.entities.Ranking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

    private final RankingRepository rankingRepository;

    @Override
    public Long addOne(RankingtBusiness entityBusiness) {
        return 0L;
    }

    @Override
    public Ranking getOne(Long id) {
        return null;
    }

    @Override
    public List<Ranking> getAll() {
        return rankingRepository.findAll();
    }

    @Override
    public void deleteOne(Long id) {

    }

    @Override
    public void updateOne(Long id, RankingtBusiness entityBusiness) {

    }
}
