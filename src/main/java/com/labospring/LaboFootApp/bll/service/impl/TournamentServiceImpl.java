package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.exceptions.DoesntExistsException;
import com.labospring.LaboFootApp.bll.exceptions.IncorrectTournamentStatusException;
import com.labospring.LaboFootApp.bll.service.TournamentService;
import com.labospring.LaboFootApp.bll.service.models.tournament.TournamentBusiness;
import com.labospring.LaboFootApp.dal.repositories.TournamentRepository;
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
    public void updateStatus(Long id, TournamentStatus tournamentStatus) {
        Tournament tournament = getOne(id);
        TournamentStatus currentStatus = tournament.getTournamentStatus();

        if (!isValidStatusTransition(currentStatus, tournamentStatus)) {
            throw new IncorrectTournamentStatusException(
                    String.format("Cannot change tournament status from %s to %s", currentStatus, tournamentStatus), 409
            );
        }

        tournament.setTournamentStatus(tournamentStatus);
        tournamentRepository.save(tournament);
    }


    private boolean isValidStatusTransition(TournamentStatus currentStatus, TournamentStatus newStatus) {
        // Logique de validation des transitions possibles
        switch (currentStatus) {
            case BUILDING:
                return newStatus == TournamentStatus.PENDING || newStatus == TournamentStatus.CANCELED;
            case PENDING:
                return newStatus == TournamentStatus.STARTED || newStatus == TournamentStatus.CANCELED;
            case STARTED:
                return newStatus == TournamentStatus.INTERRUPTED || newStatus == TournamentStatus.CLOSED;
            case INTERRUPTED:
                return newStatus == TournamentStatus.STARTED || newStatus == TournamentStatus.CANCELED;
            case CLOSED:
            case CANCELED:
                return false; // Un tournoi fermé ou annulé ne peut plus changer de statut
            default:
                return false;
        }
    }


    @Override
    public List<Tournament> findAllByUser(User user) {
        return tournamentRepository.findAllByUser(user);
    }
}
