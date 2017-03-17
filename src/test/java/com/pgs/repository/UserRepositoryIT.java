package com.pgs.repository;

import com.pgs.model.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wkloc on 2017-02-24.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
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