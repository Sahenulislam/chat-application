package com.sahenul.chat_application.user;


import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Service;


@Service
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User getCurrentUser(){
        return null; // (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    public User getUser(Long id){
        User user=userRepository.findById(id).orElse(null);
        if(user==null){
            throw new MessageConversionException("User id is not valid");
        }

        return user;
    }

    public User getCurrentUser(String userName){
        User user=userRepository.findByUserName(userName);
        if(user==null){
            throw new MessageConversionException("User id is not valid");
        }

        return user;
    }


    public void create(User user) {
        if(user.getUserName()==null){
            throw new ArithmeticException("user name must not be null");
        }
        userRepository.save(user);
    }


}
