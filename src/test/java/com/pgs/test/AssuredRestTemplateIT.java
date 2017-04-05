package com.pgs.test;

/**
 * Created by mmalek on 3/24/2017.
 */

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.port;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AssuredRestTemplateIT {

    @Value("${schooldaily.server.protocol}")
    protected String oauthServerProtocol;
    @Value("${schooldaily.server.domain}")
    protected String oauthServerDomain;
    @Value("${schooldaily.server.port}")
    protected int oauthServerPort;

    @Value("${schooldaily.oauth2.access.user}")
    protected String oauthResourceUsername;
    @Value("${schooldaily.oauth2.access.password}")
    protected String oauthResourcePassword;
    @Value("${schooldaily.oauth2.access.clientID}")
    protected String oauthResourceClientID;
    @Value("${schooldaily.oauth2.access.clientSecret}")
    protected String oauthResourceClientSecret;
    @Value("${schooldaily.oauth2.access.grantType}")
    protected String oauthResourceGrantType;
    @Value("${schooldaily.oauth2.access.scope}")
    protected String oauthResourceScope;

    String accessToken;

    @Before
    public void setup() {
        RestAssured.baseURI = oauthServerProtocol + "://" + oauthServerDomain;
        RestAssured.keystore("keystore.p12", "pgssoft");
        RestAssured.port = oauthServerPort;
    }

    private void authenticateUser(String username, String password) {

        String response =
                given()
                        .parameters("username", username, "password", password,
                                "grant_type", "password", "scope", "read write",
                                "client_id", oauthResourceClientID, "client_secret", oauthResourceClientSecret)
                        .auth()
                        .preemptive()
                        .basic(oauthResourceClientID,oauthResourceClientSecret)
                        .when()
                        .post("/oauth/token")
                        .asString();

        JsonPath jsonPath = new JsonPath(response);
        accessToken = jsonPath.getString("access_token");
    }

    @Test
    public void testGetUserDefaultUserOwner() {


        authenticateUser(oauthResourceUsername, oauthResourcePassword);

//        User user =
                given()
                        .auth().oauth2(accessToken)
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .expect()
                        .log().all()
                        .statusCode(HttpStatus.OK.value())
                        .when()
                        .get("/secure")
//                        .as(User.class);
                        .then().statusCode(200).and().equals("Hello from protected resource!");

//        assertThat(user).isEqualTo(testData.user1);
    }

    @Test
    public void test1() throws InterruptedException {
        given().port(port).basePath("/unsecure").get("").then().statusCode(200);
    }

    @Test
    public void testGetUserDefaultUserOwner2() {


        authenticateUser(oauthResourceUsername, oauthResourcePassword);

//        Principal user =
        given()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .expect()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .when()
                .get("/secure/user2")
//                        .as(Principal.class);
                .then().statusCode(200);

//        System.out.println(user);
//        assertThat(user).isEqualTo(testData.user1);
    }

}