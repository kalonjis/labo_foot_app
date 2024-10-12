package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.exceptions.DoesntExistsException;
import com.labospring.LaboFootApp.bll.exceptions.IncorrectTournamentStatusException;
import com.labospring.LaboFootApp.bll.service.RankingService;
import com.labospring.LaboFootApp.bll.service.TournamentService;
import com.labospring.LaboFootApp.bll.service.models.TournamentBusiness;
import com.labospring.LaboFootApp.dal.repositories.TournamentRepository;
import com.labospring.LaboFootApp.dl.entities.Ranking;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.dl.enums.TournamentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final RankingService rankingService;

    @Override
    public Long addOne(TournamentBusiness entityBusiness) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Tournament tournament = entityBusiness.toEntity();
        if(user != null)
            tournament.setCreator(user);

        return tournamentRepository.save(tournament).getId();
    }

    @Override
    public Tournament getOne(Long id) {
        return tournamentRepository.findById(id).orElseThrow(() -> new DoesntExistsException("No Tournament with ID " + id));
    }

    @Override
    public boolean tournamentExists(Long id){
        return tournamentRepository.existsById(id);
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

    @Override
    public void updateStatus(Long id, TournamentStatus newStatus) {
        Tournament tournament = getOne(id);
        TournamentStatus currentStatus = tournament.getTournamentStatus();

        if (!currentStatus.isValidStatusTransition(newStatus)) {
            throw new IncorrectTournamentStatusException(
                    String.format("Cannot change tournament status from %s to %s", currentStatus, newStatus), 409
            );
        }

        tournament.setTournamentStatus(newStatus);
        tournamentRepository.save(tournament);

        if (tournament.getTournamentType().isGroupStage()){
            List<Ranking> rankings = rankingService.getAllByTournamentId(id);
            if(newStatus == TournamentStatus.STARTED){
                for(Ranking r : rankings){
                    rankingService.openRanking(r);
                }
            }
            if (newStatus != TournamentStatus.STARTED){
                for(Ranking r : rankings){
                    rankingService.closeRanking(r);
                }
            }
        }
    }





    @Override
    public List<Tournament> findAllByUser(User user) {
        return tournamentRepository.findAllByUser(user);
    }
}
