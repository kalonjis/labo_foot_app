package com.labospring.LaboFootApp.bll.events;

import com.labospring.LaboFootApp.dal.repositories.FavoriteFootMatchRepository;
import com.labospring.LaboFootApp.dl.entities.FavoriteFootMatch;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.pl.controller.websocket.WebSocketController;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScoreUpdateEventListener {

    private final FavoriteFootMatchRepository favoriteFootMatchRepository;
    private final WebSocketController webSocketController;

    @EventListener
    public void handleScoreUpdateEvent(ScoreUpdateEvent event) {
        FootMatch match = event.getMatch();

        // Fetch all users subscribed to the match (if needed for other purposes, but not for WebSocket)
        List<FavoriteFootMatch> subscriptions = favoriteFootMatchRepository.findAllNotificationsByFootMatch(match);

        // Create the message with the updated score
        String message = String.format("Score updated for match: %s vs %s. New score: %d-%d",
                match.getTeamHome().getName(), match.getTeamAway().getName(),
                match.getScoreTeamHome(), match.getScoreTeamAway());

        webSocketController.sendScoreUpdate(0L, message);
        // Send real-time updates via WebSocket only to users who have notifications activated
        for (FavoriteFootMatch subscription : subscriptions) {
            // Send the score update to the specific user
            webSocketController.sendScoreUpdateToUser(subscription.getUser().getUsername(), message);
        }
    }
}
