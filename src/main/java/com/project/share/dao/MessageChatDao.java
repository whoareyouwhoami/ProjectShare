package com.project.share.dao;

import com.project.share.model.MessageChat;
import com.project.share.model.Project;
import com.project.share.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageChatDao extends JpaRepository<MessageChat, Integer> {
    MessageChat findByProjectAndUser(Project project, User user);
}
