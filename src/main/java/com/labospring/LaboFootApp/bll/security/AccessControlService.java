package com.labospring.LaboFootApp.bll.security;

import com.labospring.LaboFootApp.bll.service.FootMatchService;
import com.labospring.LaboFootApp.bll.service.TournamentService;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccessControlService {

    private final TournamentService tournamentService;

    private final FootMatchService footMatchService;

    public boolean isOrganizerTournament(User user, Long tournamentId) {
        Tournament tournament = tournamentService.getOne(tournamentId);

        return isSameUser(user, tournament.getCreator());
    }

    public boolean isModeratorMatch(User user, Long matchId) {
        FootMatch match = footMatchService.getOne(matchId);

        return isSameUser(user, match.getUserModerator());
    }

    public boolean isOrganizerMatch(User user, Long matchId) {
        FootMatch match = footMatchService.getOne(matchId);

        return isSameUser(user, match.getTournament().getCreator());
    }

    public boolean isSameUser(User firstUser, User secondUser){
        return firstUser != null && secondUser != null && Objects.equals(firstUser.getId(), secondUser.getId());
    }
}