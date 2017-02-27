package com.pgs.controller;

import facebook4j.*;
import facebook4j.auth.AccessToken;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
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
    public String facebook(Authentication authentication) throws FacebookException, MalformedURLException {
//        addNewPostOnFacebbok();
        return "facebook end point";
    }

    private void addNewPostOnFacebbok() throws FacebookException, MalformedURLException {
        // Generate facebook instance.
        Facebook facebook = new FacebookFactory().getInstance();
        // Use default values for oauth app id.
        facebook.setOAuthAppId("", "");
        // Get an access token from:
        // https://developers.facebook.com/tools/explorer
        // Copy and paste it below.
        String accessTokenString = "EAADLLpBj5EsBAPlKwOZBBsnJeA5aUMSVPguLfDp2Tl7qD1jODyaDDzm0aVmo6BqYiHhE26F8X5MK1fOBRZCr26MQFqwy4AIvpoZAEBb0ZAhJbWAhaTMZAOVtMflq4KTiY7QQH7TumnutgDnlE8YZAQFG3BZBLKgKNZBWAP9SFbRfnwZDZD";
        AccessToken at = new AccessToken(accessTokenString);
        // Set access token.
        facebook.setOAuthAccessToken(at);

        // We're done.
        // Access group feeds.
        // You can get the group ID from:
        // https://developers.facebook.com/tools/explorer

        // Set limit to 25 feeds.
//        ResponseList<Post> feeds = facebook.getFeed("187446750783", new Reading().limit(25));
        ResponseList<Post> feeds = facebook.getFeed("100015140357290", new Reading().limit(25));

        // For all 25 feeds...
        for (int i = 0; i < feeds.size(); i++) {
            // Get post.
            Post post = feeds.get(i);
            // Get (string) message.
            String message = post.getMessage();
            // Print out the message.
            System.out.println(message);

            // Get more stuff...
            PagableList<Comment> comments = post.getComments();
            String date = post.getCreatedTime().toString();
//            String name = post.getFrom().getName();
            String id = post.getId();
        }


//        facebook.commentPhoto("158026038045407", "It's a nice photo!");
        facebook.postLink(new URL("http://facebook4j.org"));
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




