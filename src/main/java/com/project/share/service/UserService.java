package com.project.share.service;

import com.project.share.model.User;

import java.util.Optional;

public interface UserService {
    void authenticateLogin(String email, String password);

    boolean userExist(String email);

    User userAdd(User u);

    User getUserByEmail(String email);

    User getUserById(int id);
}
