package com.project.share.dao;

import com.project.share.model.MessageProject;
import com.project.share.model.MessageProjectUser;
import com.project.share.model.User;
import com.project.share.model.keys.MessageProjectUserKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageProjectUserDao extends JpaRepository<MessageProjectUser, Integer> {
    MessageProjectUser findByUserAndMessageProject(User user, MessageProject messageProject);

    MessageProjectUser findById(MessageProjectUserKey key);
}
