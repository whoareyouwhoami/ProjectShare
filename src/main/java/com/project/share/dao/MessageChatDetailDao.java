package com.project.share.dao;

import com.project.share.model.MessageChatDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageChatDetailDao extends JpaRepository<MessageChatDetail, Integer> {
}
