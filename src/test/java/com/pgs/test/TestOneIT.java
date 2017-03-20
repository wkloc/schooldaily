package com.pgs.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by mmalek on 3/20/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource(locations = "classpath:application.properties")
//@SpringBootTest(properties = {"spring.cloud.config.enabled:true",
//        "spring.cloud.config.uri=https://localhost:8440","ENCRYPT_KEY=pgsadmin"}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestOneIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void exampleTest() {
        String body = this.restTemplate.getForObject("/unsecure/message", String.class);
        assertThat(body).isEqualTo("Message from DEV server TEST");
    }
}
