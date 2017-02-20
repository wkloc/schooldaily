package com.pgs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@SpringBootApplication

public class SchooldailyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchooldailyApplication.class, args);
		//this is test change to test jenkins build trigger (rev next)
	}
}
