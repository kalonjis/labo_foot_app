package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.service.FootMatchService;
import com.labospring.LaboFootApp.bll.service.models.FootMatchBusiness;
import com.labospring.LaboFootApp.bll.service.models.ScoreBusiness;
import com.labospring.LaboFootApp.dal.repositories.FootMatchRepository;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.enums.MatchStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FootMatchServiceImpl implements FootMatchService {
    private final FootMatchRepository footMatchRepository;


    @Override
    public Long addOne(FootMatchBusiness entityBusiness) {
        return footMatchRepository.save(entityBusiness.toEntity()).getId();
    }

    @Override
    public FootMatch getOne(Long id) {
        return footMatchRepository.findById(id).orElseThrow(() -> new RuntimeException("No Football Match with ID : " + id));
    }

    @Override
    public List<FootMatch> getAll() {
        return footMatchRepository.findAll();
    }

    @Override
    public void deleteOne(Long id) {
        FootMatch footMatch = getOne(id);
        footMatchRepository.delete(footMatch);
    }

    @Override
    public void updateOne(Long id, FootMatchBusiness entityBusiness) {
        FootMatch footMatch = getOne(id);


        footMatchRepository.save(footMatch);

    }

    @Override
    public void changeStatus(Long id, MatchStatus matchStatus){
        FootMatch footMatch = getOne(id);

        footMatch.setMatchStatus(matchStatus);

        footMatchRepository.save(footMatch);
    }

    @Override
    public void changeScore(Long id, ScoreBusiness scoreBusiness) {
        FootMatch footMatch = getOne(id);
        footMatch.setScoreTeamAway(scoreBusiness.scoreAway());
        footMatch.setScoreTeamHome(scoreBusiness.scoreHome());

        footMatchRepository.save(footMatch);
    }

}
