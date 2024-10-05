package com.labospring.LaboFootApp.il.config;

import com.sun.security.auth.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

/**
 * Custom handshake handler for WebSocket connections, used to assign a random user ID (UUID)
 * to each connection. This allows tracking of different WebSocket clients with unique IDs.
 */
public class UserHandshakeHandler extends DefaultHandshakeHandler {

    // Logger for logging connection events
    private final Logger LOG = LoggerFactory.getLogger(UserHandshakeHandler.class);

    /**
     * Overrides the default method to determine the user for the WebSocket session.
     * This method assigns a unique random ID to each user that connects via WebSocket.
     *
     * @param request The HTTP request initiating the WebSocket connection.
     * @param wsHandler The WebSocket handler responsible for managing WebSocket messages.
     * @param attributes Additional attributes for the WebSocket session.
     * @return A new {@link UserPrincipal} containing a unique random ID for the WebSocket connection.
     */
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        // Generate a random UUID for the user
        final String randomId = UUID.randomUUID().toString();

        // Log the event with the generated random ID
        LOG.info("User with ID '{}' opened the page", randomId);

        // Return a new UserPrincipal object with the generated ID
        return new UserPrincipal(randomId);
    }
}