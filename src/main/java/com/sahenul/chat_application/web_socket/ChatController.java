package com.sahenul.chat_application.web_socket;


import com.sahenul.chat_application.message.MessageService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    public ChatController(SimpMessagingTemplate messagingTemplate, MessageService messageService) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
    }


    @MessageMapping("/chat/{receiverId}") // Mapped to STOMP endpoint /app/chat
    public void sendMessageToUser(@DestinationVariable String receiverId, String message) {
        messageService.sendMessageToUser(receiverId, message);

    }


    @MessageMapping("/group-chat/{group-id}") // Mapped to STOMP endpoint /app/chat
    public void sendMessageToGroup(@DestinationVariable Long groupId, String message) {
        messageService.sendMessageToGroup(groupId, message);
    }
}

