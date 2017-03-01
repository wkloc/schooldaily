package com.pgs.service.api;


import com.pgs.dto.SocialUserDTO;
import org.springframework.security.core.Authentication;

public interface UserTaskService {

    void checkFacebookUserInDB(Authentication authentication);

    void checkGithubUserInDB(Authentication authentication);

    void loginOrCreateUser(SocialUserDTO dto);

    void checkGoogleUserInDB(Authentication authentication);
}
