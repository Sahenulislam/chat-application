package com.sahenul.chat_application.message;

import com.sahenul.chat_application.group.Group;
import com.sahenul.chat_application.group.GroupService;
import com.sahenul.chat_application.user.User;
import com.sahenul.chat_application.user.UserService;
import com.sahenul.chat_application.web_socket.WebSocketSessionTracker;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageRepository messageRepository;
    private final WebSocketSessionTracker webSocketSessionTracker;
    private final UserService userService;
    private final GroupService groupService;

    public MessageService(
            SimpMessagingTemplate messagingTemplate,
            MessageRepository messageRepository,
            WebSocketSessionTracker webSocketSessionTracker,
            UserService userService,
            GroupService groupService) {
        this.messagingTemplate = messagingTemplate;
        this.messageRepository = messageRepository;
        this.webSocketSessionTracker = webSocketSessionTracker;
        this.userService = userService;
        this.groupService = groupService;
    }


    public void sendMessageToUser(String receiverId, String pMessage) {
        User sender = userService.getCurrentUser();

        User receiver = userService.getCurrentUser(receiverId);


        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(pMessage);
        message.setTimestamp(LocalDateTime.now());
        message.setSeen(false);
        message.setDelivered(webSocketSessionTracker.isUserOnline(receiver.getUserName())); // Check online status


        messageRepository.save(message);

        // If the receiver is online, send the pMessage via WebSocket
        if (webSocketSessionTracker.isUserOnline(receiver.getUserName())) {
            messagingTemplate.convertAndSendToUser(receiver.getUserName(), "/queue/messages", pMessage);
        }
    }


    public void sendMessageToGroup(Long groupId, String pMessage) {

        Group group = groupService.getCurrentUserGroup(groupId);

        User sender = userService.getCurrentUser();

        Message message = new Message();
        message.setSender(sender);
        message.setContent(pMessage);
        message.setGroup(group);
        message.setTimestamp(LocalDateTime.now());
        message.setSeen(false);

        messageRepository.save(message);

        // If the receiver is online, send the pMessage via WebSocket
        for (User member : group.getGroupMemberList()) {
            if (webSocketSessionTracker.isUserOnline(member.getUserName())) {
                messagingTemplate.convertAndSendToUser(member.getUserName(), "/topic/messages", pMessage);
            }
        }
    }


    public List<ConversationDto> getConversationList(){
        User user=userService.getCurrentUser();
        return null;//messageRepository.getConversationList(user);
    }





}
