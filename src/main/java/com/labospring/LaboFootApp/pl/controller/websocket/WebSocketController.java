package com.labospring.LaboFootApp.pl.controller.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class WebSocketController {
    // This is used to send messages to specific topics.
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Send a score update to all users subscribed to the match
    public void sendScoreUpdate(Long matchId, String message) {
        messagingTemplate.convertAndSend("/notifications", message);
    }

    public void sendScoreUpdateToUser(String userId, String message) {
        // Use user-specific destination (/user/{userId}/notifications) to send the message
        log.info("Sending message to user {}: {}", userId, message);
        messagingTemplate.convertAndSendToUser(userId, "/notifications", message);
    }
}
