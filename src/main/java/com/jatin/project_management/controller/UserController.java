package com.jatin.project_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jatin.project_management.dto.UserRegistrationDto;
import com.jatin.project_management.entity.User;
import com.jatin.project_management.service.UserService;

/**
 * UserController
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@GetMapping("/my")
	public ResponseEntity<User> getCurrentUser() {
		return ResponseEntity.ok(userService.getCurrentUser());
	}

	@PutMapping
	public ResponseEntity<User> updateUser(@RequestBody UserRegistrationDto updatedUser) {
		return ResponseEntity.ok(userService.updateUser(updatedUser));
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteUser(@RequestBody UserRegistrationDto updatedUser) {
		userService.deleteUser();
		return ResponseEntity.noContent().build();
	}

}
