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
public class ScoreUpdateEventListener {

    private final WebSocketNotifier webSocketNotifier;
    private final UserService userService;

    @EventListener
    public void handleScoreUpdateEvent(ScoreUpdateEvent event) {
        FootMatch match = event.getMatch();

        String message = String.format("Score updated for match: %s vs %s. New score: %d-%d",
                match.getTeamHome().getName(), match.getTeamAway().getName(),
                match.getScoreTeamHome(), match.getScoreTeamAway());

        var usersWithNotif = userService.getUsersForMatchAndTeamsWithNotifications(match.getId(), List.of(match.getTeamHome().getId(), match.getTeamAway().getId()));
        // Send real-time updates via WebSocket only to users who have notifications activated
        for (User subscription : usersWithNotif) {
            webSocketNotifier.sendFootMatchToUser(subscription, match, message);
        }
    }
}
