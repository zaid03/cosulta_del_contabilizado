package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.backend.sqlserver1.repository.EntRepository;
import com.example.backend.dto.EntNomProjection;

import java.util.Optional;

@RestController
@RequestMapping("/api/ent")
public class EntController {
    @Autowired
    private EntRepository entRepository;

    //fetch entidad name
    @GetMapping("/name/{ent}")
    public ResponseEntity<?> getEntName(
        @PathVariable Integer ent
    ) {
        try{
            Optional<EntNomProjection> name = entRepository.findProjectedByENTCOD(ent);
            if(name.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sin resultado");
            }

            return ResponseEntity.ok(name.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getClass().getSimpleName() + e.getMessage());
        }     
    }
}
