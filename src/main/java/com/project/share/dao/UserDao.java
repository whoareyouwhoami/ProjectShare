package com.project.share.dao;

import com.project.share.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

    User findByEmail(String email);
}