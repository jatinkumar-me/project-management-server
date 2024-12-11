package com.jatin.project_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jatin.project_management.dto.AuthResponseDto;
import com.jatin.project_management.dto.UserLoginDto;
import com.jatin.project_management.dto.UserRegistrationDto;
import com.jatin.project_management.entity.User;
import com.jatin.project_management.service.AuthService;

/**
 * AuthController
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
		User registeredUser = authService.register(userRegistrationDto);
		return ResponseEntity.ok(registeredUser);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponseDto> authenticateUser(@RequestBody UserLoginDto authenticationRequest) {
		AuthResponseDto response = authService.verify(authenticationRequest);
		return ResponseEntity.ok(response);
	}
}
