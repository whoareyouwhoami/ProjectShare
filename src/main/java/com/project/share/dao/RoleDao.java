package com.project.share.dao;

import com.project.share.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleDao extends JpaRepository<Role, Integer> {
    @Override
    List<Role> findAll();
}
