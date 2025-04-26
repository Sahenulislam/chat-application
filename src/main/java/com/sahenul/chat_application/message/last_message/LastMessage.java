package com.sahenul.chat_application.message.last_message;


import com.sahenul.chat_application.chat_group.ChatGroup;
import com.sahenul.chat_application.message.Message;
import com.sahenul.chat_application.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data

public class LastMessage {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    @ManyToOne
    private ChatGroup chatGroup;

    @ManyToOne
    private Message message;

    private LocalDateTime timestamp;
}
