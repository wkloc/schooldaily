package com.pgs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping()
    public String hello() {
        return "Hello from protected resource!";
    }

    @GetMapping(value = "/user2", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Principal resource(Principal principal) {
        return principal;
    }

    @GetMapping(value = "/facebook")
    @ResponseBody
    public String facebook() {
        return "facebook end point";
    }

    @GetMapping({"/user", "/me"})
    public Map<String, String> user(Principal principal) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", principal.getName()); //facebook id also
        return map;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/country")
    public String country(Locale locale) {
        return locale.getCountry() + " (only for ADMIN)";
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<String> getString(@PathVariable Integer id) {
        return new ResponseEntity<>("string" + id, HttpStatus.OK);
    }
}




