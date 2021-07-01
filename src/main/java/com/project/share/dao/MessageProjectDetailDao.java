package com.project.share.dao;

import com.project.share.model.MessageProjectDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageProjectDetailDao extends JpaRepository<MessageProjectDetail, Integer> {
}
