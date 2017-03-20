package com.pgs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class SchooldailyApplication {

	static {
		//for localhost testing only
		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
				new javax.net.ssl.HostnameVerifier(){

					public boolean verify(String hostname,
										  javax.net.ssl.SSLSession sslSession) {
						if (hostname.equals("localhost")) {
							return true;
						}
						return false;
					}
				});
	}

	public static void main(String[] args) {
		SpringApplication.run(SchooldailyApplication.class, args);
	}
}
