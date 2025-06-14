package com.sahenul.chat_application.user;


import com.sahenul.chat_application.security.custom_user_details.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;


    public Object getCurrentUser(OAuth2User principal){
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("userName", principal.getAttribute("userName"));
        attributes.put("name", principal.getAttribute("name"));
        attributes.put("email", principal.getAttribute("email"));
        attributes.put("accessToken", principal.getAttribute("accessToken"));
        return attributes;
    }


    public Object getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

         if (authentication == null || !authentication.isAuthenticated()) {
            return null;  // No authenticated user
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof OAuth2User) {
            return getCurrentUser((OAuth2User) principal);// Google OAuth2 login
        }else if (principal instanceof UserDetails) {
             return findByEmail(((UserDetails) principal).getUsername());// Spring Security login (e.g.)
        }

        return null; // Unknown principal type
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

    public User getCurrentUser(Long id){
        User user=userRepository.findById(id).orElse(null);
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


    public List<User> list(String userName, String name) {
        return userRepository.userList(userName,name);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public List<User> getAllWithoutMe() {
        User user=(User) getCurrentUser();

        return userRepository.findAllWithoutMe(user.getId());
    }
}
