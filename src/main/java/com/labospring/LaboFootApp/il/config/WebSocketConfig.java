package com.labospring.LaboFootApp.il.config;


import com.labospring.LaboFootApp.il.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


/**
 * WebSocket configuration class that sets up the WebSocket connection and message routing,
 * including JWT-based authentication and handling of user-specific messaging.
 */
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    /**
     * Configures the message broker to enable simple message routing to clients
     * and sets destination prefixes for messages from and to clients.
     *
     * @param config The registry for configuring message broker options.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable a simple message broker for client-subscription to "/notifications" topic
        config.enableSimpleBroker("/notifications");

        // Prefix for client-to-server messages, typically in the "/app" namespace
        config.setApplicationDestinationPrefixes("/app");

        // Prefix for sending messages to specific users (e.g., /user/{userId}/notifications)
        config.setUserDestinationPrefix("/user");
    }

    /**
     * Registers the WebSocket endpoint that clients will use to establish connections.
     * Also enables SockJS fallback options in case WebSocket is not supported.
     *
     * @param registry The registry used to register WebSocket endpoints.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Define the WebSocket endpoint and allow connections from the specified origin
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://127.0.0.1:5500") // Origin allowed for WebSocket connections
                .setHandshakeHandler(new UserHandshakeHandler()) // Custom handshake handler for user identity
                .withSockJS(); // Enable SockJS fallback if WebSocket is not available
    }

    /**
     * Configures the inbound channel for processing messages, allowing us to intercept
     * WebSocket connection attempts and authenticate the user using JWT.
     *
     * @param registration The channel registration object used to register interceptors.
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // Register a custom interceptor to handle WebSocket messages
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                // Access the STOMP headers from the incoming message
                // STOMP is the Simple (or Streaming) Text Oriented Messaging Protocol.
                // It uses a set of commands like CONNECT, SEND, or SUBSCRIBE to manage the conversation.
                // STOMP clients, written in any language, can talk with any message broker supporting the protocol.
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
                    // Extract the JWT token from the "Authorization" header
                    String authorizationHeader = accessor.getFirstNativeHeader("Authorization");
                    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
                        String username = jwtUtils.getUsername(token); // Extract username from the token

                        // Load user details using the extracted username
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                        // Create an authentication object with the user's authorities
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        // Set the authentication in the SecurityContext
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                        // Attach the user to the STOMP connection
                        accessor.setUser(authenticationToken);
                    }
                }

                // Continue processing the message
                return message;
            }
        });
    }
}
