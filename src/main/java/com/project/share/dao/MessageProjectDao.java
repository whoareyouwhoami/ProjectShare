package com.project.share.dao;

import com.project.share.model.MessageProject;
import com.project.share.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageProjectDao extends JpaRepository<MessageProject, Integer> {
    MessageProject findByProject(Project project);
}
