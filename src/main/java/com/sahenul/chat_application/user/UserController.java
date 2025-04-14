package com.sahenul.chat_application.user;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user){
        userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    @GetMapping("/list")
    public ResponseEntity<?> List(
            @RequestParam (required = false) String userName,
            @RequestParam (required = false) String name
    ){

        return ResponseEntity.ok().body( userService.list(userName,name));
    }


    @GetMapping("/me")
    public Object getUserInfo() {

        return userService.getCurrentUser();


    }




}
