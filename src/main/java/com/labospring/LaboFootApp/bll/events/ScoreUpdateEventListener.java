package com.labospring.LaboFootApp.bll.events;

import com.labospring.LaboFootApp.bll.service.FavoriteFootMatchService;
import com.labospring.LaboFootApp.dl.entities.FavoriteFootMatch;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScoreUpdateEventListener {

    private final FavoriteFootMatchService favoriteFootMatchService;
    private final WebSocketNotifier webSocketNotifier;

    @EventListener
    public void handleScoreUpdateEvent(ScoreUpdateEvent event) {
        FootMatch match = event.getMatch();

        // Fetch all users subscribed to the match (if needed for other purposes, but not for WebSocket)
        List<FavoriteFootMatch> subscriptions = favoriteFootMatchService.getAllNotificationsByFootMatch(match);

        // Create the message with the updated score
        String message = String.format("Score updated for match: %s vs %s. New score: %d-%d",
                match.getTeamHome().getName(), match.getTeamAway().getName(),
                match.getScoreTeamHome(), match.getScoreTeamAway());
        // Send real-time updates via WebSocket only to users who have notifications activated
        for (FavoriteFootMatch subscription : subscriptions) {
            webSocketNotifier.sendFootMatchToUser(subscription.getUser(), match, message);

            // Send the score update to the specific user
//            webSocketController.sendScoreUpdateToUser(subscription.getUser().getUsername(), new NotificationFootMatchScore(
//                    match.getTeamHome().getName(), match.getTeamAway().getName(), match.getScoreTeamHome(), match.getScoreTeamAway(), message
//            ));
        }
    }
}
