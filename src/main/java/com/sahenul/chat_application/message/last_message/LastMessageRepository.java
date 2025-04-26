package com.sahenul.chat_application.message.last_message;

import com.sahenul.chat_application.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LastMessageRepository extends JpaRepository<LastMessage,Long> {
    LastMessage findBySenderIdAndReceiverId(Long id, Long id1);

    List<LastMessage> findBySender(User user);
}
