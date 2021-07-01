package com.project.share.dao;

import com.project.share.model.MessageDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDetailDao_TEMPO extends JpaRepository<MessageDetail, Integer> {
}
