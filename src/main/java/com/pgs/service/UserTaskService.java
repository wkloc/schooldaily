package com.pgs.service;


import com.pgs.dto.SocialUserDTO;
import org.springframework.security.core.Authentication;

public interface UserTaskService {

    void checkFacebookUserInDB(Authentication authentication);

    void checkGithubUserInDB(Authentication authentication);

    void loginOrCreateUser(SocialUserDTO dto);
}
