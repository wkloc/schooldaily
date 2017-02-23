package com.pgs.service;


import com.pgs.dto.FacebookUserDTO;

public interface UserTaskService {

	void loginOrCreateFacebookUser(FacebookUserDTO dto);
	
}
