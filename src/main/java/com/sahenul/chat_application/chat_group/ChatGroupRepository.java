package com.sahenul.chat_application.chat_group;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatGroupRepository extends JpaRepository<ChatGroup,Long> {
    //Group findByIdAndGroupMember(Long id, User currentUser);
}
