package com.project.share.service;

import com.project.share.dao.RoleDao;
import com.project.share.dao.UserDao;
import com.project.share.exception.UserException;
import com.project.share.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    @Qualifier("UserDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Override
    public void authenticateLogin(String email, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        // Authenticate
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // If success proceed
        if (usernamePasswordAuthenticationToken.isAuthenticated())
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        log.info("Login Success...");
    }

    @Override
    public boolean userExist(String email) {
        return userDao.existsByEmail(email);
    }

    @Override
    public User userAdd(User user) {
        String pw = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(pw);
        user.setPasswordConfirm(pw);
        user.setRoles(roleDao.findAll());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        return userDao.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao
                .findByEmail(email)
                .orElseThrow(
                        () -> new UserException("User doesn't exist")
                );
    }
}
