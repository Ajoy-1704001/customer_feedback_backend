package com.deb.customer_feedback_backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deb.customer_feedback_backend.dto.JwtResponse;
import com.deb.customer_feedback_backend.dto.LoginRequest;
import com.deb.customer_feedback_backend.dto.SignUpRequest;
import com.deb.customer_feedback_backend.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody SignUpRequest entity) {
		return authService.registerUser(entity);
	}
	
	@PostMapping("/login")
	public JwtResponse postMethodName(@RequestBody LoginRequest entity) {
		return authService.authenticateUser(entity);
	}
	
	
}
