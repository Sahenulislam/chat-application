package com.sahenul.chat_application.group;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")

@RequestMapping("api/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;


    @PostMapping
    public ResponseEntity<?> create(@RequestBody Group group){
        groupService.create(group);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Group group){
        groupService.update(group);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{group-id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long groupId){
        groupService.delete(groupId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-user/{group-id}")
    public ResponseEntity<?> deleteUsers(@PathVariable Long groupId, @RequestBody List<Long> userList){
        groupService.deleteUsers(groupId,userList);
        return ResponseEntity.noContent().build();
    }

}
