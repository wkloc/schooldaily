package com.pgs.service.impl;

import com.pgs.dto.SocialUserDTO;
import com.pgs.enums.ESocialType;
import com.pgs.model.Authority;
import com.pgs.model.SocialUserDetails;
import com.pgs.model.Users;
import com.pgs.repository.UserRepository;
import com.pgs.service.api.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.UUID;

@Component("userTaskService")
public class UserTaskServiceImpl implements UserTaskService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void checkFacebookUserInDB(Authentication authentication){
		if (authentication.isAuthenticated()){
			String tokenValue = ((OAuth2AuthenticationDetails)((OAuth2Authentication) authentication).getDetails()).getTokenValue();
			Facebook facebook = new FacebookTemplate(tokenValue, "schooldaily");
			String [] fields = { "id", "email",  "first_name", "last_name" };
			User user =  facebook.fetchObject("me", User.class, fields);
			SocialUserDTO facebookUserDTO = new SocialUserDTO(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), null, ESocialType.FACEBOOK);
			loginOrCreateUser(facebookUserDTO);
		}
	}

	@Override
	public void checkGithubUserInDB(Authentication authentication) {
		if (authentication.isAuthenticated()){
			LinkedHashMap<String, Object> details = (LinkedHashMap<String, Object>) ((OAuth2Authentication) authentication).getUserAuthentication().getDetails();
			String login = (String)details.get("login");
			String id = details.get("id").toString();
			String avatar_url = (String)details.get("avatar_url");
			String email = (String)details.get("email");
			String name = (String)details.get("name");
			SocialUserDTO user = new SocialUserDTO(id, email, login, name, avatar_url, ESocialType.GITHUB);
			loginOrCreateUser(user);
		}
	}

	@Override
	public void checkGoogleUserInDB(Authentication authentication) {
		if (authentication.isAuthenticated()) {
			LinkedHashMap<String, String> details = (LinkedHashMap<String, String>) ((OAuth2Authentication) authentication).getUserAuthentication().getDetails();
			String login = details.get("sub");
			String id = details.get("sub");
			String avatar_url = details.get("picture");
			String email = details.get("email");
			String firstname = details.get("given_name");
			String lastname = details.get("family_name");
			SocialUserDTO user = new SocialUserDTO(id, email, firstname, lastname, avatar_url, ESocialType.GOOGLE);
			loginOrCreateUser(user);
		}
	}

	@Override
	public void loginOrCreateUser(SocialUserDTO dto) {
		ESocialType socialType = dto.getSocialType();
		Users user = null;
			if (socialType == ESocialType.GITHUB) {
				user = this.userRepository.findByGithubId(dto.getId());
			} else if (socialType == ESocialType.FACEBOOK) {
				user = this.userRepository.findByFacebookId(dto.getId());
			} else if (socialType == ESocialType.GOOGLE) {
				user = this.userRepository.findByGoogleId(dto.getId());
			}

			if (null == user) {
				user = new Users();
				user.setPassword(UUID.randomUUID().toString());
				user.setEmail(dto.getEmail());
				user.setFirstname(dto.getFirstname());
				user.setLastname(dto.getLastname());

				if (socialType == ESocialType.FACEBOOK) {
					user.setUsername("FB_" + dto.getId());
					user.setFacebookId(dto.getId());
					user.setFacebookImage("//graph.facebook.com/_PIC_URL_/picture?height=285&width=285".replace("_PIC_URL_", dto.getId()));
				} else if (socialType == ESocialType.GITHUB) {
					user.setUsername("GH_" + dto.getId());
					user.setGithubId(dto.getId());
					user.setGithubImage(dto.getImage());
				} else if (socialType == ESocialType.GOOGLE) {
					user.setUsername("GL_" + dto.getId());
					user.setGoogleId(dto.getId());
					user.setGoogleImage(dto.getImage());
				}

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
