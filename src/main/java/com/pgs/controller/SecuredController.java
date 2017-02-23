package com.pgs.controller;

import com.pgs.dto.FacebookUserDTO;
import com.pgs.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by mmalek on 2/14/2017.
 */
@RestController
@RequestMapping("/secure")
public class SecuredController {

    @Autowired
    UserTaskService userTaskService;

    @RequestMapping(method = RequestMethod.GET)
    public String hello() {
        return "Hello from protected resource!";
    }

    @RequestMapping(value = "/user2", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Principal resource(Principal principal) {
        return principal;
    }

    @GetMapping(value = "/facebook")
    @ResponseBody
    public String facebook(Authentication authentication){

        OAuth2Authentication auth = (OAuth2Authentication) authentication;
        String userDetailsFromAuthentication = ((OAuth2Authentication) auth.getUserAuthentication()).getUserAuthentication().getDetails().toString();

        String tokenValue = ((OAuth2AuthenticationDetails) ((OAuth2Authentication) auth.getUserAuthentication()).getDetails()).getTokenValue();
        Facebook facebook = new FacebookTemplate(tokenValue, "schooldaily");

        String [] fields = { "id", "email",  "first_name", "last_name" };
        User user =  facebook.fetchObject("me", User.class, fields);

        FacebookUserDTO facebookUserDTO = new FacebookUserDTO(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName());

        if (authentication.isAuthenticated()) {
            userTaskService.loginOrCreateFacebookUser(facebookUserDTO);
        }

        return facebookUserDTO.toString();

    }

    @RequestMapping({"/user", "/me"})
    public Map<String, String> user(Principal principal) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", principal.getName()); //facebook id also
        return map;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/country", method = RequestMethod.GET)
    public String country(Locale locale) {
        return locale.getCountry() + " (only for ADMIN)";
    }

}




