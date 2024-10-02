package com.labospring.LaboFootApp.bll.events;

import com.labospring.LaboFootApp.bll.service.UserService;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.util.List;


/**
 * The StatusUpdateEventListener listens for status update events and sends real-time notifications
 * to users who have subscribed to receive updates about specific matches or teams.
 */
@Component
@RequiredArgsConstructor
public class StatusUpdateEventListener {

    private final WebSocketNotifier webSocketNotifier;
    private final UserService userService;

    /**
     * Handles the status update event and sends a real-time notification to users who have subscribed
     * to updates for the specific match or the teams involved.
     *
     * This method listens for StatusUpdateEvent and sends a notification when the status of a match is updated.
     * It runs asynchronously to avoid blocking the main thread.
     *
     * @param event The event containing the updated match status.
     */
    @Async
    @EventListener
    public void handleScoreUpdateEvent(StatusUpdateEvent event) {
        FootMatch match = event.getMatch();

        // Create a notification message with the updated match status
        String message = String.format("Status updated for match: %s vs %s. New status: %s",
                match.getTeamHome().getName(), match.getTeamAway().getName(),
                match.getMatchStatus());

        // Fetch users who have notifications enabled for this match or teams
        var usersWithNotif = userService.getUsersForMatchAndTeamsWithNotifications(
                match.getId(), List.of(match.getTeamHome().getId(), match.getTeamAway().getId()));

        for (User subscription : usersWithNotif) {
                webSocketNotifier.setStatusFootMatchUser(subscription, match, message);
        }
    }
}
