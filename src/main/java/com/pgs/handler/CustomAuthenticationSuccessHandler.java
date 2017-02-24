package com.pgs.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mmalek on 2/23/2017.
 */
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {

//        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/uncesure/"+authentication.getName());
        super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
        System.out.println("onAuthenticationSuccess");
    }
}