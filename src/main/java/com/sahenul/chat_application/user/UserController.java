package com.sahenul.chat_application.user;


import com.sahenul.chat_application.group.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user){
        userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



//    @GetMapping("/list")
//    public ResponseEntity<?> List(){
//        userService.create(user);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }




}
