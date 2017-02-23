package com.pgs.service;

import com.pgs.dto.FacebookUserDTO;
import com.pgs.model.Authority;
import com.pgs.model.SocialUserDetails;
import com.pgs.model.Users;
import com.pgs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@Component("userTaskService")
public class UserTaskServiceImpl implements UserTaskService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void loginOrCreateFacebookUser(FacebookUserDTO dto) {

		Users user = this.userRepository.findByFacebookId(dto.getId());

		if(null == user){
			user = new Users();

			String facebookUserId = dto.getId();

			user.setUsername(facebookUserId);
			user.setPassword(UUID.randomUUID().toString());
			user.setEmail(dto.getEmail());

			user.setFacebookId(facebookUserId);
			user.setFacebookImage("//graph.facebook.com/_PIC_URL_/picture?height=285&width=285".replace("_PIC_URL_", facebookUserId));

			Authority authority = new Authority();
			authority.setName("ROLE_USER");
			user.setAuthorities(Collections.singleton(authority));

			user = this.userRepository.save(user);
		}

		SocialUserDetails userDetails = new SocialUserDetails(user.getUsername(), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

		SecurityContextHolder.clearContext();
		SecurityContextHolder.getContext().setAuthentication(authentication);

//		this.rememberMeServices.loginSuccess(request, response, authentication);
	}
}
