package com.pgs.controller;

import com.pgs.config.ClientResources;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.support.OAuth1Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
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

//    @Inject
//    private Environment environment;
//
//
//
//    @Bean
//    @ConfigurationProperties("facebook")
//    public ClientResources facebook() {
//        System.out.println("###############################################################" +
//        environment.getProperty("facebook.client.clientId")
//        );
//        return new ClientResources();
//    }

//
//    public SecuredController(Facebook facebook, ConnectionRepository connectionRepository) {
//        this.facebook = facebook;
//        this.connectionRepository = connectionRepository;
//    }


//    @RequestMapping(value = "/facebook")
//    public String facebook(Model model){
//
//        return "facebook: " + facebook().getClient().getAccessTokenUri();
//    }





    //    @RequestMapping(value="/facebook", method=RequestMethod.GET)
//    public String home(P) {
//
//        RestTemplate rest = new RestTemplate();
//        String jsonResponse = rest.getForObject("https://graph.facebook.com/me/?fields=" +  "email,id,last_name" + "&access_token=" + accessToken, String.class);
//
//        JSONObject o = JSONObject.fromObject(jsonResponse);
//
//        Connection<Facebook> connection = connectionRepository.findPrimaryConnection(Facebook.class);
//        if (connection == null) {
//            return "redirect:/connect/facebook";
//        }
//
//        return connection.getApi().userOperations().getUserProfile().getEmail();
//    }


    @RequestMapping(method = RequestMethod.GET)
    public String hello() {
        return "Hello from protected resource!";
    }

    @RequestMapping(value = "/user2", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Principal resource(Principal principal) {
        return principal;
    }

    @GetMapping(value = "/facebook", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, String> facebook(Authentication authentication){

        OAuth2Authentication auth = (OAuth2Authentication) authentication;
        String s = ((OAuth2Authentication) auth.getUserAuthentication()).getUserAuthentication().getDetails().toString();

        String tokenValue = ((OAuth2AuthenticationDetails) ((OAuth2Authentication) auth.getUserAuthentication()).getDetails()).getTokenValue();
        Facebook facebook = new FacebookTemplate(tokenValue, "schooldaily");

        String [] fields = { "id", "email",  "first_name", "last_name" };
//        String [] fields = { "id", "about", "age_range", "birthday", "context", "cover", "currency", "devices", "education", "email", "favorite_athletes", "favorite_teams", "first_name", "gender", "hometown", "inspirational_people", "installed", "install_type", "is_verified", "languages", "last_name", "link", "locale", "location", "meeting_for", "middle_name", "name", "name_format", "political", "quotes", "payment_pricepoints", "relationship_status", "religion", "security_settings", "significant_other", "sports", "test_group", "timezone", "third_party_id", "updated_time", "verified", "video_upload_limits", "viewer_can_send_gift", "website", "work"};
        User user =  facebook.fetchObject("me", User.class, fields);

        Map<String, String> map = new LinkedHashMap<>();
        map.put(fields[0], user.getId());
        map.put(fields[1], user.getEmail());
        map.put(fields[2], user.getFirstName());
        map.put(fields[3], user.getLastName());
        return map;

    }

    @RequestMapping({"/user", "/me"})
    public Map<String, String> user(Principal principal) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", principal.getName());
        return map;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/country", method = RequestMethod.GET)
    public String country(Locale locale) {
        return locale.getCountry() + " (only for ADMIN)";
    }

}


