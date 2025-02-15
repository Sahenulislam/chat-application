package com.sahenul.chat_application.group;

import com.sahenul.chat_application.message.Message;
import com.sahenul.chat_application.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "groups")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;

    @OneToMany
    private List<User> groupMemberList; // List of users in the group

    @OneToMany
    private List<Message> groupMessageList; // List of messages in the group
}
