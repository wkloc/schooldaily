package com.pgs.repository;

import com.pgs.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by mmalek on 2/14/2017.
 */
public interface UserRepository extends JpaRepository<Users, String> {

    @Query("SELECT u FROM Users u WHERE LOWER(u.username) = LOWER(:username)")
    Users findByUsernameCaseInsensitive(@Param("username") String username);

    Users findByFacebookId(String facebookId);

    Users findByGithubId(String githubId);

    Users findByGoogleId(String id);
}
