package com.project.share.config.security;

import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        /* Allow only POST method */
        if(!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Method not allowed: " + request.getMethod());
        }

        /* Get user email and password */
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        /* Process input values */
        email = email == null ? "" : email.trim();
        password = password == null ? "" : password.trim();

        /* Check user information */
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
        this.setDetails(request, token);
        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    @Nullable
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter("email");
    }
}
