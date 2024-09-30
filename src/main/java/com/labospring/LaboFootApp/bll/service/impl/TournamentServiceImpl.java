package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.exceptions.DoesntExistsException;
import com.labospring.LaboFootApp.bll.security.AccessControlService;
import com.labospring.LaboFootApp.bll.security.AuthService;
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

//TODO: GERER LA CREATION DE RANKING LORS DE LA CREATION DE TOURNAMENT AVEC GROUPE


@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;

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
    public void updateStatus(Long id, TournamentStatus tournamentStatus) {
        Tournament tournament = getOne(id);
        tournament.setTournamentStatus(tournamentStatus);
        tournamentRepository.save(tournament);
    }

    @Override
    public List<Tournament> findAllByUser(User user) {
        return tournamentRepository.findAllByUser(user);
    }
}
