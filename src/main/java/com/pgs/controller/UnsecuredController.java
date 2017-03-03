package com.pgs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by mmalek on 2/14/2017.
 */
@Controller
@RequestMapping("/unsecure")
public class UnsecuredController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        return "Hello from unsecured endpoint!";
    }

    @GetMapping(value = "/logoutmessage")
    @ResponseBody
    public String logoutMessage() {
        return "LOGOUT message!";
    }
}
