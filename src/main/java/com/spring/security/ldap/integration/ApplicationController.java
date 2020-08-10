package com.spring.security.ldap.integration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

	@GetMapping("/")
	public String home() {
		return "Welcome to the Homeland";
	}
}
