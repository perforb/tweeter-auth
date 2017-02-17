package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableAuthorizationServer
public class TweeterAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(TweeterAuthApplication.class, args);
	}

	@Configuration
	static class LoginConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.formLogin().loginPage("/login").permitAll().and().requestMatchers()
					.antMatchers("/", "/login", "/oauth/authorize",
							"/oauth/confirm_access")
					.and().authorizeRequests().anyRequest().authenticated();
		}
	}
}
