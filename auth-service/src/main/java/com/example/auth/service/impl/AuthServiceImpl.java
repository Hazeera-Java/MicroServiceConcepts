package com.example.auth.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.example.auth.dto.LoginRequest;
import com.example.auth.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	private final JwtEncoder jwtEncoder;
	private final PasswordEncoder passwordEncoder;

	public AuthServiceImpl(JwtEncoder jwtEncoder, PasswordEncoder passwordEncoder) {

		this.jwtEncoder = jwtEncoder;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public String login(LoginRequest request) {

		/*
		 * Learning-project credentials.
		 *
		 * Later we can replace this with User Service / database validation.
		 */
		String username = "admin";
		String password = "password";

		if (!request.getUsername().equals(username) || !request.getPassword().equals(password)) {

			throw new RuntimeException("Invalid username or password");
		}

		Instant now = Instant.now();

		JwtClaimsSet claims = JwtClaimsSet.builder().issuer("microservices-auth").subject(username).issuedAt(now)
				.expiresAt(now.plus(1, ChronoUnit.HOURS)).claim("role", "USER").build();

		JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

		return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
	}
}