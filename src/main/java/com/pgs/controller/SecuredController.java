package com.pgs.controller;

import com.pgs.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
        return "facebook end point";
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




