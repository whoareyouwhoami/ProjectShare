package com.project.share.config.security;

import com.project.share.controller.StarterController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailHandler implements AuthenticationFailureHandler {
    static final Logger log = LoggerFactory.getLogger(CustomAuthenticationFailHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String email = httpServletRequest.getParameter("email");
        String password = httpServletRequest.getParameter("password");
        log.info("Attempting login: " + email + " with password: " + password);

        if(e instanceof BadCredentialsException || e instanceof InternalAuthenticationServiceException) {
            log.error("Bad credential");
            httpServletRequest.setAttribute("errorMessage", "Invalid email or password");
        }
        httpServletRequest.getRequestDispatcher("/login?error=true").forward(httpServletRequest, httpServletResponse);
    }
}
