package com.parcelflow.userservice.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.parcelflow.userservice.dto.UserRequestDTO;
import com.parcelflow.userservice.dto.UserResponseDTO;
import com.parcelflow.userservice.entity.User;
import com.parcelflow.userservice.exception.UserNotFoundException;
import com.parcelflow.userservice.repository.UserRepository;
import com.parcelflow.userservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	// Field injection @Autowired
	private final UserRepository userRepository;

	// Constructor injection
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserResponseDTO createUser(UserRequestDTO request) {

		log.info("Creating new user with email: {}", request.getEmail());
		User user = new User();

		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPhone(request.getPhone());

		User savedUser = userRepository.save(user);
		log.info("User created successfully with id: {}", savedUser.getId());

		return convertToResponseDTO(savedUser);
	}

	public UserResponseDTO getUserById(Long id) {
		log.info("Fetching user with id: {}", id);
		User user = userRepository.findById(id).orElseThrow(() -> {
			log.warn("User not found with id: {}", id);
			return new UserNotFoundException(id);
		});

		log.info("User found with id: {}", id);

		return convertToResponseDTO(user);
	}

	public List<UserResponseDTO> getAllUsers() {
		return userRepository.findAll().stream().map(this::convertToResponseDTO).toList();
	}

	public boolean deleteUserById(Long id) {
		log.info("Deleting user with id: {}", id);

		if (!userRepository.existsById(id)) {
			log.warn("Cannot delete. User not found with id: {}", id);
			throw new UserNotFoundException(id);
		}
		userRepository.deleteById(id);
		log.info("User deleted successfully with id: {}", id);
		return true;
	}

	public UserResponseDTO updateUser(Long id, UserRequestDTO request) {
		log.info("Updating user with id: {}", id);

		User existingUser = userRepository.findById(id).orElseThrow(() -> {
			log.warn("Cannot update. User not found with id: {}", id);
			return new UserNotFoundException(id);
		});

		existingUser.setName(request.getName());
		existingUser.setEmail(request.getEmail());
		existingUser.setPhone(request.getPhone());

		User updatedUser = userRepository.save(existingUser);

		log.info("User updated successfully with id: {}", id);

		return convertToResponseDTO(updatedUser);
	}

	private UserResponseDTO convertToResponseDTO(User user) {

		return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone());
	}
}
