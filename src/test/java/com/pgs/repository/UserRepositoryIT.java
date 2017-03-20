package com.pgs.repository;

import com.pgs.model.Users;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wkloc on 2017-02-24.
 */
//@Ignore
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@TestPropertySource(locations = "classpath:application.properties")
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = NONE)

    @Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:application.properties")


public class UserRepositoryIT extends AbstractRepositoryIT {

    private final static String USERNAME = "username";
    private final static String PASSWORD = "password";
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsernameCaseInsensitive() {
        Users user = new Users();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);

        Users persistedUser = this.userRepository.save(user);

        assertThat(user.getUsername()).isEqualTo(persistedUser.getUsername());
        assertThat(user.getPassword()).isEqualTo(persistedUser.getPassword());


        Users userA = userRepository.findByUsernameCaseInsensitive("username");
        assertThat(userA).isEqualTo(persistedUser);

        Users userB = userRepository.findByUsernameCaseInsensitive("Username");
        assertThat(userB).isEqualTo(persistedUser);

        Users userC = userRepository.findByUsernameCaseInsensitive("UserName");
        assertThat(userC).isEqualTo(persistedUser);

        Users userD = userRepository.findByUsernameCaseInsensitive("username  ");
        assertThat(userD).isNotEqualTo(persistedUser);
    }
}