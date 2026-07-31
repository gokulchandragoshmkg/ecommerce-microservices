package com.gcg.authservice.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // In production, load this from Config Server, never hardcode
    private final SecretKey secretKey = Keys.hmacShaKeyFor(
        "your-256-bit-secret-your-256-bit-secret".getBytes());

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600_000)) // 1 hour
                .signWith(secretKey)
                .compact();
    }
}