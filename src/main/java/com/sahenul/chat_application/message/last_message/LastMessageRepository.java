package com.sahenul.chat_application.message.last_message;

import com.sahenul.chat_application.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LastMessageRepository extends JpaRepository<LastMessage,Long> {
    LastMessage findBySenderIdAndReceiverId(Long id, Long id1);


//    @Query(value = """
//         select *from last_message where sender_id =:id
//            """,nativeQuery = true)

    @Query(value = """ 
         select lm from LastMessage lm where lm.sender=:user
            """)
    List<LastMessage> findBySender(User user);
}
