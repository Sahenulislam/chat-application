package com.sahenul.chat_application.user;


import org.springframework.stereotype.Service;

@Service
public class UserService {


    public User getCurrentUser(){
        return null; // (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
