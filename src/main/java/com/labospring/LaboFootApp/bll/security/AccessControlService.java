package com.labospring.LaboFootApp.bll.security;

import com.labospring.LaboFootApp.bll.service.FootMatchService;
import com.labospring.LaboFootApp.bll.service.TournamentService;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccessControlService {

    private final TournamentService tournamentService;

    private final FootMatchService footMatchService;

    // Vérifier si l'utilisateur a créé le tournoi
    public boolean canManageTournament(Long tournamentId) {
        Tournament tournament = tournamentService.getOne(tournamentId);

        User user = getAuthentification();
        if(user == null) return false;
        // Vérifie si l'utilisateur est le créateur du tournoi
        return isSameUser(user, tournament.getCreator());
    }

    // Vérifier si l'utilisateur est modérateur du match
    public boolean canManageMatch(User user, Long matchId) {
        FootMatch match = footMatchService.getOne(matchId);

        // Vérifie si l'utilisateur est le modérateur de ce match
        return isSameUser(user, match.getTournament().getCreator()) || isSameUser(user, match.getUserModerator());
    }

    public User getAuthentification(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User ?(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal() : null;
    }

    public boolean isSameUser(User firstUser, User secondUser){
        return Objects.equals(firstUser.getId(), secondUser.getId());
    }
}