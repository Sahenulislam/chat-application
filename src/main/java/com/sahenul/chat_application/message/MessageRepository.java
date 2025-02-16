package com.sahenul.chat_application.message;

import com.sahenul.chat_application.group.Group;
import com.sahenul.chat_application.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

//
//    @Query("""
//        (SELECT m.sender.id, m.sender.userName, MAX(m.timestamp)
//         FROM Message m
//         WHERE m.receiver.id = :userId
//         GROUP BY m.sender.id, m.sender.userName)
//
//        UNION
//
//        (SELECT m.receiver.id, m.receiver.userName, MAX(m.timestamp)
//         FROM Message m
//         WHERE m.sender.id = :userId
//         GROUP BY m.receiver.id, m.receiver.userName)
//
//        UNION
//
//        (SELECT m.group.id, m.group.name, MAX(m.timestamp)
//         FROM Message m
//         WHERE m.group IS NOT NULL AND
//               :userId MEMBER OF m.group.groupMemberList
//         GROUP BY m.group.id, m.group.name)
//
//        ORDER BY MAX(m.timestamp) DESC
//    """)


    @Query("""
        SELECT 
            m.id, 
            CASE 
                WHEN m.group IS NOT NULL THEN m.group.groupName
                WHEN m.sender = :user THEN m.receiver.userName
                ELSE m.sender.userName
            END,
            MAX(m.timestamp),
            CASE 
                WHEN m.group IS NOT NULL THEN true
                ELSE false
            END
        FROM Message m
        WHERE 
            m.sender = :user OR m.receiver = :user OR 
            (m.group IS NOT NULL AND :user MEMBER OF m.group.groupMemberList)
        GROUP BY 
            CASE 
                WHEN m.group IS NOT NULL THEN m.group.id
                WHEN m.sender = :user THEN m.receiver.id
                ELSE m.sender.id
            END, 
            m.id, m.group.groupName, m.sender.userName, m.receiver.userName
        ORDER BY MAX(m.timestamp) DESC
    """)
    List<Object[]> findConversationList(User user);


    @Query("""
            SELECT m from Message as m
            WHERE (m.sender=:partner 
            AND m.receiver=:currentUser)
            OR (m.receiver=:partner
            AND m.sender=:currentUser)
            ORDER by m.timestamp
            """)
    List<Message> findConversationByUser(User curentUser, User partner);


    @Query("""
            SELECT m from Message as m
            where m.group=:group
            ORDER by m.timestamp
            """)
    List<Message> findConversationByGroup(Group group);
}
