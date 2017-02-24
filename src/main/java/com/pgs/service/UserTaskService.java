package com.pgs.service;


import com.pgs.dto.FacebookUserDTO;
import org.springframework.security.core.Authentication;

public interface UserTaskService {

    void checkFacebookUserInDB(Authentication authentication);

    void loginOrCreateFacebookUser(FacebookUserDTO dto);
	
}
