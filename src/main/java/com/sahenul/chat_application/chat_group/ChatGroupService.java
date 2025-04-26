package com.sahenul.chat_application.chat_group;

import com.sahenul.chat_application.user.User;
import com.sahenul.chat_application.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatGroupService {

    private final ChatGroupRepository chatGroupRepository;
    private final UserService userService;

    public void create(ChatGroup chatGroup) {
        chatGroupRepository.save(chatGroup);
    }



    public ChatGroup getGroup(Long id){
        ChatGroup chatGroup = chatGroupRepository.findById(id).orElse(null);
        if(chatGroup ==null){
            throw new RuntimeException("Group id is not valid");
        }
        return chatGroup;


    }

    public ChatGroup getCurrentUserGroup(Long id){
        ChatGroup chatGroup =null;//groupRepository.findByIdAndGroupMember(id, userService.getCurrentUser());

        if(chatGroup ==null){
            throw new RuntimeException("Group is not valid");
        }
        return chatGroup;
    }


    public void update(ChatGroup pChatGroup) {
        ChatGroup chatGroup =getGroup(pChatGroup.getId());
        if(chatGroup.getGroupName()!=null) chatGroup.setGroupName(chatGroup.getGroupName());

        for(User user: pChatGroup.getGroupMemberList()){
            if(!chatGroup.getGroupMemberList().contains(user)){
                chatGroup.getGroupMemberList().add(user);
            }
        }
    }

    public void delete(Long groupId) {
        ChatGroup chatGroup =getGroup(groupId);
        chatGroupRepository.delete(chatGroup);
    }

    public void deleteUsers(Long groupId, List<Long> userList) {
        ChatGroup chatGroup =getGroup(groupId);
        for(Long userId:userList){
            User user=userService.getUser(userId);
            if(chatGroup.getGroupMemberList().contains(user)){
                chatGroup.getGroupMessageList().remove(user);
            }
        }
    }
}
