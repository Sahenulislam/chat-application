package com.sahenul.chat_application.group;

import com.sahenul.chat_application.user.User;
import com.sahenul.chat_application.user.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class GroupService {

    private final GroupRepository groupRepository;
    private final UserService userService;

    public void create(Group group) {
        groupRepository.save(group);
    }


    public GroupService(GroupRepository groupRepository, UserService userService) {
        this.groupRepository = groupRepository;
        this.userService = userService;
    }


    public Group getGroup(Long id){
        Group group=groupRepository.findById(id).orElse(null);
        if(group==null){
            throw new RuntimeException("Group id is not valid");
        }
        return group;


    }

    public Group getCurrentUserGroup(Long id){
        Group group=null;//groupRepository.findByIdAndGroupMember(id, userService.getCurrentUser());

        if(group==null){
            throw new RuntimeException("Group is not valid");
        }
        return group;
    }


    public void update(Group pGroup) {
        Group group=getGroup(pGroup.getId());
        if(group.getGroupName()!=null)group.setGroupName(group.getGroupName());

        for(User user: pGroup.getGroupMemberList()){
            if(!group.getGroupMemberList().contains(user)){
                group.getGroupMemberList().add(user);
            }
        }
    }

    public void delete(Long groupId) {
        Group group=getGroup(groupId);
        groupRepository.delete(group);
    }

    public void deleteUsers(Long groupId, List<Long> userList) {
        Group group=getGroup(groupId);
        for(Long userId:userList){
            User user=userService.getUser(userId);
            if(group.getGroupMemberList().contains(user)){
                group.getGroupMessageList().remove(user);
            }
        }
    }
}
