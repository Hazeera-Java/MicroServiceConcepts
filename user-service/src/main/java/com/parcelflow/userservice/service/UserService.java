package com.parcelflow.userservice.service;

import java.util.List;

import com.parcelflow.userservice.dto.UserRequestDTO;
import com.parcelflow.userservice.dto.UserResponseDTO;

public interface UserService {

	UserResponseDTO createUser(UserRequestDTO user);

	UserResponseDTO getUserById(Long id);

	List<UserResponseDTO> getAllUsers();

	UserResponseDTO updateUser(Long id, UserRequestDTO user);

	boolean deleteUserById(Long id);
}
