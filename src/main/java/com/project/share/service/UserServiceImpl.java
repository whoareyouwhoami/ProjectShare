package com.project.share.service;

import com.project.share.dao.RoleDao;
import com.project.share.dao.UserDao;
import com.project.share.exception.UserException;
import com.project.share.model.Role;
import com.project.share.model.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationProvider authenticationProvider;

    public UserServiceImpl(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationProvider authenticationProvider) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public void authenticateLogin(User user, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
        authenticationProvider.authenticate(usernamePasswordAuthenticationToken);

        if(usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } else {
            throw new UserException("User registration error");
        }
    }

    @Override
    public boolean userExist(String email) {
        return userDao.existsByEmail(email);
    }

    @Override
    public User userAdd(User user) {
        /* NEED SOME CHANGE... */
        User u = new User();
        String pw = bCryptPasswordEncoder.encode(user.getPassword());
        u.setPassword(pw);
        u.setPasswordConfirm(pw);
        u.setAccountNonExpired(true);
        u.setAccountNonLocked(true);
        u.setCredentialsNonExpired(true);
        u.setEnabled(true);

        u.setEmail(user.getEmail());
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        u.setGender(user.getGender());
        u.setDateOfBirth(user.getDateOfBirth());

        Set<Role> userRoleSet = new HashSet<>();
        userRoleSet.add(roleDao.findByName("USER"));

        u.setRoles(userRoleSet);
        return userDao.save(u);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            return this.userDao.findByEmail(email);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Invalid email address");
        }
    }
}
