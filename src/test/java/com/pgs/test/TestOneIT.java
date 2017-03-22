package com.pgs.test;

import com.pgs.model.Users;
import com.pgs.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.fail;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by mmalek on 3/20/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestOneIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void exampleTest() {
        String body = this.restTemplate.getForObject("/unsecure/message", String.class);
        assertThat(body).isEqualTo("Message from DEV server TEST");
        fail();
    }

    @Test
    public void repoTest() {

        Users user = new Users();
        user.setUsername("test1");
        user.setPassword("test2");

        Users persistedUser = this.userRepository.save(user);
        Users user1 = userRepository.findByUsernameCaseInsensitive("test1");

        assertThat(user1.getUsername()).isEqualTo(persistedUser.getUsername());
    }
}
