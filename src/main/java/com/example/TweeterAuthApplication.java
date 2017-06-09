package com.example;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

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

		/**
		 * http://docs.spring.io/spring-security/site/docs/4.2.3.RELEASE/reference/html/jc.html#ldap-authentication
		 */
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.ldapAuthentication() //
					.userDnPatterns("uid={0},ou=people") //
					.groupSearchBase("ou=groups") //
					.contextSource(contextSource()) //
					.passwordCompare() //
					.passwordEncoder(new LdapShaPasswordEncoder())
					.passwordAttribute("userPassword");
		}

		@Bean
		DefaultSpringSecurityContextSource contextSource() {
			return new DefaultSpringSecurityContextSource(
					Arrays.asList("ldap://localhost:8389/"), "dc=springframework,dc=org");
		}
	}

	@Configuration
	@EnableResourceServer
	static class ResourceServerConfig extends ResourceServerConfigurerAdapter {
		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
					.authorizeRequests().antMatchers(HttpMethod.GET, "/userinfo")
					.access("#oauth2.hasScope('openid')");
		}
	}
}
