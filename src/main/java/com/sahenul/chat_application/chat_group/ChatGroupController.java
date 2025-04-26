package com.sahenul.chat_application.chat_group;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/group")
@RequiredArgsConstructor
public class ChatGroupController {

    private final ChatGroupService chatGroupService;


    @PostMapping
    public ResponseEntity<?> create(@RequestBody ChatGroup chatGroup){
        chatGroupService.create(chatGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ChatGroup chatGroup){
        chatGroupService.update(chatGroup);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{group-id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long groupId){
        chatGroupService.delete(groupId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-user/{group-id}")
    public ResponseEntity<?> deleteUsers(@PathVariable Long groupId, @RequestBody List<Long> userList){
        chatGroupService.deleteUsers(groupId,userList);
        return ResponseEntity.noContent().build();
    }

}
