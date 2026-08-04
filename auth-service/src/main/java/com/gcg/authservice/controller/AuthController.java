package com.gcg.authservice.controller;

import com.gcg.authservice.entity.User;
import com.gcg.authservice.service.UserService;
import com.gcg.authservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/authservice")
public class AuthController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest request) {
		User user = userService.findByUsername(request.getUsername());

		if (!jwtUtil.passwordEncoder().matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}

		return ResponseEntity.ok(jwtUtil.generateToken(user.getUsername()));
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(jwtUtil.passwordEncoder().encode(request.getPassword()));
		user.setEmail(request.getEmail());
		userService.saveUser(user);
		return ResponseEntity.ok("Registered successfully");
	}

}