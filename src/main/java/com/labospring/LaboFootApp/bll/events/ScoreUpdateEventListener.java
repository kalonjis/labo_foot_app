package com.labospring.LaboFootApp.bll.events;


import com.labospring.LaboFootApp.bll.service.UserService;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.util.List;
import org.springframework.scheduling.annotation.Async;

/**
 * The ScoreUpdateEventListener listens for score update events and sends real-time notifications to users
 * who have subscribed to receive updates about specific matches or teams.
 */
@Component
@RequiredArgsConstructor
public class ScoreUpdateEventListener {

    private final WebSocketNotifier webSocketNotifier;
    private final UserService userService;

    /**
     * Handles the score update event and sends a real-time notification to users who have subscribed
     * to updates for the specific match or the teams involved.
     *
     * This method listens for ScoreUpdateEvent and sends a notification when a match score is updated.
     * It runs asynchronously to avoid blocking the main thread.
     *
     * @param event The event containing the updated match information.
     */
    @Async
    @EventListener
    public void handleScoreUpdateEvent(ScoreUpdateEvent event) {
        FootMatch match = event.getMatch();

        // Create a notification message with the updated score
        String message = String.format("Score updated for match: %s vs %s. New score: %d-%d",
                match.getTeamHome().getName(), match.getTeamAway().getName(),
                match.getScoreTeamHome(), match.getScoreTeamAway());

        // Fetch users who have notifications enabled for this match or teams
        var usersWithNotif = userService.getUsersForMatchAndTeamsWithNotifications(
                match.getId(), List.of(match.getTeamHome().getId(), match.getTeamAway().getId()));

        for (User subscription : usersWithNotif) {
            webSocketNotifier.sendFootMatchToUser(subscription, match, message);
        }
    }
}
