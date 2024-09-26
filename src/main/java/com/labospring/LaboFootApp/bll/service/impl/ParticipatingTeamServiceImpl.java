package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.service.ParticipatingTeamService;
import com.labospring.LaboFootApp.bll.service.RankingService;
import com.labospring.LaboFootApp.bll.service.models.ParticipatingTeamBusiness;
import com.labospring.LaboFootApp.bll.service.models.RankingBusiness;
import com.labospring.LaboFootApp.dal.repositories.ParticipatingTeamRepository;
import com.labospring.LaboFootApp.dl.entities.ParticipatingTeam;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipatingTeamServiceImpl implements ParticipatingTeamService {

    private final RankingService rankingService;
    private final ParticipatingTeamRepository participatingTeamRepository;

//    Tournament tournament = entityBusiness.tournament();
//        if(tournament.getTournamentType().isGroupStage()){
//        if(tournament.getRankingList().isEmpty()){
//            for (int i = 0; i < tournament.getTournamentType().getNbGroups(); i++) {
//                rankingService.addOne(new RankingBusiness(tournament.getId(), 1));
//            }
//
//        }
//    }

    @Override
    public Long addOne(ParticipatingTeamBusiness entityBusiness) {


        return 0L;
    }

    @Override
    public ParticipatingTeam getOne(Long aLong) {
        return null;
    }

    @Override
    public List<ParticipatingTeam> getAll() {
        return participatingTeamRepository.findAll();
    }

    @Override
    public void deleteOne(Long aLong) {

    }

    @Override
    public void updateOne(Long aLong, ParticipatingTeamBusiness entityBusiness) {

    }
}
