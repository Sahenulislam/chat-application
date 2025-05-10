package com.sahenul.chat_application.web_socket;


import com.sahenul.chat_application.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;


    @MessageMapping("/chat/{receiverId}") // Mapped to STOMP endpoint /app/chat/{receiverId}
    public void sendMessageToUser(@DestinationVariable Long receiverId,
                                  String message,
                                  Principal principal) {
        String senderEmail = principal.getName(); // Retrieved from JWT
        System.out.println("Sender: " + senderEmail + ", Receiver: " + receiverId + ", Message: " + message);

        // Pass sender info to the service
        messageService.sendMessageToUser(senderEmail, receiverId, message);
    }


    @MessageMapping("/group-chat/{group-id}") // Mapped to STOMP endpoint /app/chat
    public void sendMessageToGroup(@DestinationVariable Long groupId, String message) {
        messageService.sendMessageToGroup(groupId, message);
    }
}

