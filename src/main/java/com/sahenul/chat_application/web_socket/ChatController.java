package com.sahenul.chat_application.web_socket;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat") // Mapped to STOMP endpoint /app/chat
    public void sendMessage(String message) {
        // Broadcast the message to all clients subscribed to /topic/messages
        messagingTemplate.convertAndSend("/topic/messages", message);
    }
}

