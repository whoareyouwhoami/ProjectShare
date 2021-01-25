package com.project.share.dao;

import com.project.share.model.Project;
import com.project.share.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDao extends JpaRepository<Project, Integer> {
    List<Project> findAllByUser(User user);
}
