package io.github.mockup.polaris.web.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mockup.polaris.web.dto.request.AuthRequest;
import io.github.mockup.polaris.web.dto.request.RegisterRequest;
import io.github.mockup.polaris.web.dto.request.TokenRefreshRequest;
import io.github.mockup.polaris.web.dto.response.AuthResponse;
import io.github.mockup.polaris.web.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest request) {
		authService.register(request);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
		authService.logout(authHeader);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refresh(@RequestBody @Valid TokenRefreshRequest request) {
		AuthResponse response = authService.refreshAccessToken(request.refreshToken());
		return ResponseEntity.ok(response);
	}
}
