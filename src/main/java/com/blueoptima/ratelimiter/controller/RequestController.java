package com.blueoptima.ratelimiter.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueoptima.ratelimiter.beans.Users;

@RestController
public class RequestController {

	
	@GetMapping("/")
	public String getHomePage()
	{
		return "Welcome to Home";
	}
	
	@GetMapping("/api/v1/developers")
	@PreAuthorize("hasRole('ROLE_USER')")
	public String getDeveloperPage()
	{
		return "Welcome to API version 1 of developers page";
	}
	
	@GetMapping("/api/v1/organizations")
	@PreAuthorize("hasRole('ROLE_USER')")
	public String getOrganizationsPage()
	{
		return "Welcome to API version 1 of organizations page";
	}
	
	/*
	 * @PostMapping("/app/registeruser")
	 * 
	 * @PreAuthorize("hasRole('ROLE_ADMIN')") public String registerUser(Users user)
	 * {
	 * 
	 * }
	 */
}
