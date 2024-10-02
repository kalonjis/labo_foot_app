package com.labospring.LaboFootApp.pl.controller.websocket;

import com.labospring.LaboFootApp.bll.events.WebSocketNotifier;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.pl.models.notif.NotificationFootMatchScore;
import com.labospring.LaboFootApp.pl.models.notif.NotificationFootMatchStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class WebSocketController implements WebSocketNotifier {
    // This is used to send messages to specific topics.
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void sendFootMatchToUser(User user, FootMatch match, String message) {
        // Send real-time updates via WebSocket only to users who have notifications activated
        log.info("Sending message to user {}: {}", user.getUsername(), "");
        messagingTemplate.convertAndSendToUser(user.getUsername(), "/notifications",  new NotificationFootMatchScore(
                match.getTeamHome().getName(),
                match.getTeamAway().getName(),
                match.getScoreTeamHome(),
                match.getScoreTeamAway(),
                message));
    }

    @Override
    public void setStatusFootMatchUser(User user, FootMatch match, String message) {
        messagingTemplate.convertAndSendToUser(user.getUsername(), "/notifications",  new NotificationFootMatchStatus(
                match.getTeamHome().getName(),
                match.getTeamAway().getName(),
                match.getMatchStatus(),
                message));
    }
}
