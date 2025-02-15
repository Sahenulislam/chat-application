package com.sahenul.chat_application.group;

import com.sahenul.chat_application.user.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service

public class GroupService {

    private final GroupRepository groupRepository;
    private final UserService userService;

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



}
