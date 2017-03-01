package com.pgs.filter;

import com.pgs.enums.ESocialType;
import com.pgs.service.api.UserTaskService;
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

    private UserTaskService userTaskService;
    private ESocialType social;

    public CustomOAuth2ClientAuthenticationProcessingFilter(UserTaskService userTaskService, String path, ESocialType social) {
        super(path);
        this.userTaskService = userTaskService;
        this.social = social;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authentication);
        if (social == ESocialType.FACEBOOK) {
            userTaskService.checkFacebookUserInDB(authentication);
        } else if (social == ESocialType.GITHUB) {
            userTaskService.checkGithubUserInDB(authentication);
        } else if (social == ESocialType.GOOGLE) {
            userTaskService.checkGoogleUserInDB(authentication);
        }
    }
}
