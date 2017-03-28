package com.pgs.test;

/**
 * Created by mmalek on 3/24/2017.
 */

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class OAuth2RestTemplateIT {

    @Value("${work.server.protocol}")
    protected String oauthServerProtocol;
    @Value("${work.server.domain}")
    protected String oauthServerDomain;
    @Value("${work.server.port}")
    protected int oauthServerPort;

    @Value("${test.oauth2.access.user}")
    protected String oauthResourceUsername;
    @Value("${test.oauth2.access.password}")
    protected String oauthResourcePassword;
    @Value("${test.oauth2.access.clientID}")
    protected String oauthResourceClientID;
    @Value("${test.oauth2.access.clientSecret}")
    protected String oauthResourceClientSecret;
    @Value("${test.oauth2.access.grantType}")
    protected String oauthResourceGrantType;
    @Value("${test.oauth2.access.scope}")
    protected String oauthResourceScope;

    private OAuth2RestTemplate restTemplate;
    private String url;

    @Before
    public void init() {
        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
        url = format("%s://%s:%d", oauthServerProtocol, oauthServerDomain, oauthServerPort);
        resourceDetails.setUsername(oauthResourceUsername);
        resourceDetails.setPassword(oauthResourcePassword);
        resourceDetails.setAccessTokenUri(url + "/oauth/token");
        resourceDetails.setClientId(oauthResourceClientID);
        resourceDetails.setClientSecret(oauthResourceClientSecret);
        resourceDetails.setGrantType(oauthResourceGrantType);
        resourceDetails.setScope(asList(oauthResourceScope.split(", ")));
        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
        restTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);
//        restTemplate.setMessageConverters(asList(new MappingJackson2HttpMessageConverter()));
    }

    @Test
    public void testRestSecure() {
        // Given
        // When
        String ret = restTemplate.getForObject(url + "/secure", String.class);
        // Then
        System.out.println(ret);
        assertThat(ret).isEqualTo("Hello from protected resource!");
    }

    @Test
    @Profile("dev")
    public void testRestUnsecure() {
        // Given
        // When
        String ret = restTemplate.getForObject(url + "/unsecure/message", String.class);
        // Then
        System.out.println(ret);
//        assertEquals(ret, "Message from DEV server");
    }

    @Test
    public void testRestUser2() {
        // Given
//        restTemplate.setMessageConverters(asList(new MappingJackson2HttpMessageConverter()));
        // When
        JsonNode ret = restTemplate.getForObject(url + "/secure/user2", JsonNode.class);
        // Then
        System.out.println(ret);
//        assertEquals(ret, "Message from DEV server");
    }
}