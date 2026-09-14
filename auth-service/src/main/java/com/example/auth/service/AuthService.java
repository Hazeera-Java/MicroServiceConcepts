package com.example.auth.service;

import com.example.auth.dto.LoginRequest;

public interface AuthService {

	String login(LoginRequest request);
}