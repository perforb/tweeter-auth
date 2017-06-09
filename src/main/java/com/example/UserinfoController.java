package com.example;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserinfoController {

	@GetMapping("userinfo")
	Object userinfo(Authentication authentication) {
		Map<String, Object> userinfo = new LinkedHashMap<>();
		userinfo.put("name", authentication.getName());
		userinfo.put("authorities", authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
		userinfo.put("firstName", "John");
		userinfo.put("lastName", "Doe");
		userinfo.put("email", "jdoe@example.com");
		return userinfo;
	}
}