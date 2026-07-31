package com.gcg.authservice.controller;

import com.gcg.authservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/authservice")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        // For now, hardcoded check — replace with real user validation later
        if ("admin".equals(request.getUsername()) && "password".equals(request.getPassword())) {
            return jwtUtil.generateToken(request.getUsername());
        }
        throw new RuntimeException("Invalid credentials");
    }
}