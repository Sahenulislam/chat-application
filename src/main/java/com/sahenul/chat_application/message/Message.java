package com.sahenul.chat_application.message;

import com.sahenul.chat_application.group.Group;
import com.sahenul.chat_application.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;  // for one to one message

    @ManyToOne
    private Group group;     // for group message

    private String content;

    private boolean isSeen;
    private boolean isDelivered;

    private LocalDateTime timestamp;
}
