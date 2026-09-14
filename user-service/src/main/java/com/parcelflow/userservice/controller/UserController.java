package com.parcelflow.userservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parcelflow.userservice.dto.UserRequestDTO;
import com.parcelflow.userservice.dto.UserResponseDTO;
import com.parcelflow.userservice.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO user) {

		UserResponseDTO savedUser = userService.createUser(user);

		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {

		UserResponseDTO user = userService.getUserById(id);

		return ResponseEntity.ok(user);
	}

	@GetMapping("/search")
	public ResponseEntity<UserResponseDTO> getUserByIdReqParam(@RequestParam Long id) {

		UserResponseDTO user = userService.getUserById(id);

		return ResponseEntity.ok(user);
	}

	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> getAllUsers() {

		List<UserResponseDTO> user = userService.getAllUsers();

		return ResponseEntity.ok(user);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {

		boolean deleted = userService.deleteUserById(id);

		if (!deleted) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO user) {

		UserResponseDTO updatedUser = userService.updateUser(id, user);

		if (updatedUser == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(updatedUser);
	}
}
