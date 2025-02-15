package com.sahenul.chat_application.group;

import com.sahenul.chat_application.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group,Long> {
    //Group findByIdAndGroupMember(Long id, User currentUser);
}
