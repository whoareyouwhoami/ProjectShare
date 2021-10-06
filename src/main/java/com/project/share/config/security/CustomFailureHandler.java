package com.project.share.config.security;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class CustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    public CustomFailureHandler() {
        super("/login");
    }

    public CustomFailureHandler(String failureUrl) {
        super(failureUrl);
    }
}
