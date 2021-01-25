package com.project.share.dao;

import com.project.share.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends JpaRepository<Role, Integer> {
    @Override
    List<Role> findAll();
}
