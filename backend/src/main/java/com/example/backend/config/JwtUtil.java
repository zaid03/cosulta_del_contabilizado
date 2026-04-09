package com.example.backend.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private final Key key;
    private final long expMillis;

    public JwtUtil(
        @Value("${security.jwt.secret}") String secret,
        @Value("${security.jwt.exp-min}") long expMin
    ){
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expMillis = expMin * 60_000;
    }

    public String generate(String username){
        Instant now = Instant.now();
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plusMillis(expMillis)))
            .signWith(key) 
            .compact();
    }

    public String validateAndGetSubject(String token){
        try {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        } catch (JwtException e) {
            return null;
        }
    }
}