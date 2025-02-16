package com.sahenul.chat_application.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserName(String userName);


    @Query("""
            SELECT u FROM User AS u
            WHERE (:userName IS NULL OR u.userName=:userName)
            AND (:name IS NULL OR u.name=:name)
            """)
    List<User> userList(String userName, String name);
}
