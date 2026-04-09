package com.example.backend.controller;

import java.util.Map;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.backend.config.JwtUtil;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class AuthController {

    private final JwtUtil jwtUtil;
    public AuthController(JwtUtil jwtUtil){ this.jwtUtil = jwtUtil; }

    @PostMapping("/cas/validate")
    public ResponseEntity<?> validateCASTicket(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        Boolean validated = Boolean.valueOf(request.get("validated"));
        
        if (username == null || !validated) {
            return ResponseEntity.badRequest()
                .body(Map.of("Éxito", false, "Error", "Nombre de usuario faltante o no validado"));
        }
        
        String token = jwtUtil.generate(username);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "username", username,
            "token", token
        ));
    }
}