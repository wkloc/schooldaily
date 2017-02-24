package com.pgs.filter;

import com.pgs.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mmalek on 2/24/2017.
 */
public class CustomOAuth2ClientAuthenticationProcessingFilter extends OAuth2ClientAuthenticationProcessingFilter {

    @Autowired
    UserTaskService userTaskService;

    public CustomOAuth2ClientAuthenticationProcessingFilter(String path) {
        super(path);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authentication);
        userTaskService.checkFacebookUserInDB(authentication);
    }
}
