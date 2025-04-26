package com.sahenul.chat_application.message;

import com.sahenul.chat_application.group.Group;
import com.sahenul.chat_application.group.GroupService;
import com.sahenul.chat_application.message.last_message.LastMessage;
import com.sahenul.chat_application.message.last_message.LastMessageRepository;
import com.sahenul.chat_application.user.User;
import com.sahenul.chat_application.user.UserService;
import com.sahenul.chat_application.web_socket.WebSocketSessionTracker;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MessageService {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageRepository messageRepository;
    private final WebSocketSessionTracker webSocketSessionTracker;
    private final UserService userService;
    private final GroupService groupService;
    private final LastMessageRepository lastMessageRepository;

    public Message getMessage(Long id) {
        Message message = messageRepository.findById(id).orElse(null);
        if (message == null) throw new RuntimeException("Message id is not valid");
        return message;
    }


    public void sendMessageToUser(String receiverId, String pMessage) {
        User sender = (User) userService.getCurrentUser();

        User receiver = userService.getCurrentUser(receiverId);


        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(pMessage);
        message.setTimestamp(LocalDateTime.now());
        message.setSeen(false);
        message.setDelivered(webSocketSessionTracker.isUserOnline(receiver.getUserName())); // Check online status


        Message messageObject = messageRepository.save(message);

        LastMessage lastMessage = lastMessageRepository.findBySenderIdAndReceiverId(sender.getId(), receiver.getId());

        LastMessage lastMessageAnother = lastMessageRepository.findBySenderIdAndReceiverId(receiver.getId(), sender.getId());

        if (lastMessage == null) {
            lastMessage = new LastMessage();
            lastMessage.setSender(sender);
            lastMessage.setReceiver(receiver);
            lastMessage.setMessage(messageObject);
            lastMessage.setGroup(null);
            lastMessageRepository.save(lastMessage);


            lastMessageAnother = new LastMessage();
            lastMessageAnother.setSender(receiver);
            lastMessageAnother.setReceiver(sender);
            lastMessageAnother.setMessage(messageObject);
            lastMessageAnother.setGroup(null);
            lastMessageRepository.save(lastMessageAnother);
        }else{
            lastMessage.setMessage(messageObject);
            lastMessageRepository.save(lastMessage);

            lastMessageAnother.setMessage(messageObject);
            lastMessageRepository.save(lastMessageAnother);
        }


        // If the receiver is online, send the pMessage via WebSocket
        if (webSocketSessionTracker.isUserOnline(receiver.getUserName())) {
            messagingTemplate.convertAndSendToUser(receiver.getUserName(), "/queue/messages", pMessage);
        }
    }


    public void sendMessageToGroup(Long groupId, String pMessage) {

        Group group = groupService.getCurrentUserGroup(groupId);

        User sender = (User) userService.getCurrentUser();

        Message message = new Message();
        message.setSender(sender);
        message.setContent(pMessage);
        message.setGroup(group);
        message.setTimestamp(LocalDateTime.now());
        message.setSeen(false);

        messageRepository.save(message);

        // If the receiver is online, send the pMessage via WebSocket
        for (User member : group.getGroupMemberList()) {
            LastMessage lastMessage=new LastMessage();
            lastMessage.setSender(member);
            lastMessage.setGroup(group);
            lastMessage.setMessage(message);
            lastMessageRepository.save(lastMessage);

            if (webSocketSessionTracker.isUserOnline(member.getUserName())) {
                messagingTemplate.convertAndSendToUser(member.getUserName(), "/topic/messages", pMessage);
            }
        }
    }


    public List<LastMessage> getConversationList() {
        User user = (User) userService.getCurrentUser();
//        List<Object[]> conversationList = messageRepository.findConversationList(user);
//
//        return conversationList.stream().map(obj -> new ConversationDto(
//                (Long) obj[0],
//                (String) obj[1],
//                (LocalDateTime) obj[2],
//                (boolean) obj[3]
//        )).collect(Collectors.toList());
        return lastMessageRepository.findBySender(user);
    }


    public List<Message> getConversation(Long partnerId) {
        User partner = userService.getUser(partnerId);
        User curentUser = (User) userService.getCurrentUser();
        return messageRepository.findConversationByUser(curentUser, partner);
    }

    public List<Message> getGroupConversation(Long groupId) {
        Group group = groupService.getGroup(groupId);

        return messageRepository.findConversationByGroup(group);
    }

    public void deleteMessage(Long id) {
        Message message = getMessage(id);
        messageRepository.delete(message);
    }
}
