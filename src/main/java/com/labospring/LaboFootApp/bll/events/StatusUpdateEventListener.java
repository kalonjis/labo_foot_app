package com.labospring.LaboFootApp.bll.events;

import com.labospring.LaboFootApp.bll.service.UserService;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StatusUpdateEventListener {
    private final WebSocketNotifier webSocketNotifier;
    private final UserService userService;

    @EventListener
    public void handleScoreUpdateEvent(StatusUpdateEvent event) {
        FootMatch match = event.getMatch();

        String message = String.format("Status updated for match: %s vs %s. New status: %s",
                match.getTeamHome().getName(), match.getTeamAway().getName(),
                match.getMatchStatus());

        var usersWithNotif = userService.getUsersForMatchAndTeamsWithNotifications(match.getId(), List.of(match.getTeamHome().getId(), match.getTeamAway().getId()));
        // Send real-time updates via WebSocket only to users who have notifications activated
        for (User subscription : usersWithNotif) {
            webSocketNotifier.setStatusFootMatchUser(subscription, match, message);
        }
    }
}
