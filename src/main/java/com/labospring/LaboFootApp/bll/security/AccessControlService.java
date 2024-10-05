package com.labospring.LaboFootApp.bll.security;

import com.labospring.LaboFootApp.bll.service.*;
import com.labospring.LaboFootApp.dl.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccessControlService {

    private final TournamentService tournamentService;
    private final FootMatchService footMatchService;
    private final TeamService teamService;
    private final RankingService rankingService;
    private final PlayerService playerService;
    private final CoachService coachService;
    private final RefereeService refereeService;

    /**
     * Checks if the given user is the organizer (creator) of the specified tournament.
     *
     * @param user        the user to check
     * @param tournamentId the ID of the tournament
     * @return true if the user is the organizer of the tournament, false otherwise
     */
    public boolean isOrganizerTournament(User user, Long tournamentId) {
        Tournament tournament = tournamentService.getOne(tournamentId);
        return isSameUser(user, tournament.getCreator());
    }

    /**
     * Checks if the given user is the moderator of the specified match.
     *
     * @param user    the user to check
     * @param matchId the ID of the match
     * @return true if the user is the moderator of the match, false otherwise
     */
    public boolean isModeratorMatch(User user, Long matchId) {
        FootMatch match = footMatchService.getOne(matchId);
        return isSameUser(user, match.getUserModerator());
    }

    /**
     * Checks if the given user is the organizer (creator) of the tournament
     * associated with the specified match.
     *
     * @param user    the user to check
     * @param matchId the ID of the match
     * @return true if the user is the organizer of the tournament for the match, false otherwise
     */
    public boolean isOrganizerMatch(User user, Long matchId) {
        FootMatch match = footMatchService.getOne(matchId);
        return isSameUser(user, match.getTournament().getCreator());
    }

    /**
     * Checks if the given user is the creator of the specified team.
     *
     * @param user   the user to check
     * @param teamId the ID of the team
     * @return true if the user is the creator of the team, false otherwise
     */
    public boolean isUserTeam(User user, Long teamId) {
        Team team = teamService.getOne(teamId);
        return isSameUser(user, team.getCreator());
    }

    /**
     * Checks if the given user is the organizer (creator) of the tournament
     * associated with the specified ranking.
     *
     * @param user      the user to check
     * @param rankingId the ID of the ranking
     * @return true if the user is the creator of the tournament for the ranking, false otherwise
     */
    public boolean isUserRanking(User user, Long rankingId) {
        Ranking ranking = rankingService.getOne(rankingId);
        return isSameUser(user, ranking.getTournament().getCreator());
    }

    /**
     * Checks if the given player was created by the specified user.
     *
     * @param user the user to check
     * @param id   the ID of the player
     * @return true if the player was created by the user, false otherwise
     */
    public boolean isPlayerCreatedByUser(User user, Long id) {
        return user.getUsername().equals(playerService.getOne(id).getCreatedBy());
    }

    /**
     * Checks if the given coach was created by the specified user.
     *
     * @param user the user to check
     * @param id   the ID of the coach
     * @return true if the coach was created by the user, false otherwise
     */
    public boolean isCoachCreatedByUser(User user, Long id) {
        return user.getUsername().equals(coachService.getOne(id).getCreatedBy());
    }

    /**
     * Checks if the given referee was created by the specified user.
     *
     * @param user the user to check
     * @param id   the ID of the referee
     * @return true if the referee was created by the user, false otherwise
     */
    public boolean isRefereeCreatedByUser(User user, Long id) {
        return user.getUsername().equals(refereeService.getOne(id).getCreatedBy());
    }

    /**
     * Helper method to check if two users are the same based on their IDs.
     *
     * @param firstUser  the first user
     * @param secondUser the second user
     * @return true if both users are non-null and have the same ID, false otherwise
     */
    private boolean isSameUser(User firstUser, User secondUser) {
        return firstUser != null && secondUser != null && Objects.equals(firstUser.getId(), secondUser.getId());
    }
}
