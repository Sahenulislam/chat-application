package com.sahenul.chat_application.web_socket;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Component
public class WebSocketSessionTracker {
    private static final Set<String> onlineUsers = new HashSet<>();

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Principal userPrincipal = headerAccessor.getUser(); // Get authenticated user

        if (userPrincipal != null) {
            String username = userPrincipal.getName(); // Extract database username
            onlineUsers.add(username);
            System.out.println(username + " is now online!");
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Principal userPrincipal = headerAccessor.getUser(); // Get authenticated user

        if (userPrincipal != null) {
            String username = userPrincipal.getName();
            onlineUsers.remove(username);
            System.out.println(username + " is now offline!");
        }
    }

    public boolean isUserOnline(String username) {
        return onlineUsers.contains(username);
    }
}
