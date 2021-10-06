package com.project.share.service;

import com.project.share.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void authenticateLogin(User user, String password);

    boolean userExist(String email);

    User userAdd(User u);

    User getUserByEmail(String email);
}
