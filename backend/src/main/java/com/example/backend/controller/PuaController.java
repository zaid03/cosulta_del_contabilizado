package com.example.backend.controller;

import com.example.backend.sqlserver1.model.Pua;
import com.example.backend.sqlserver1.repository.PuaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pua")
public class PuaController {

    @Autowired
    private PuaRepository puaRepository;

    @GetMapping("/filter/{usucod}")
    public ResponseEntity<?> getFilteredData(
        @PathVariable String usucod
    ) {
        try{
            List<Pua> entidades = puaRepository.findByUSUCODAndAPLCOD(usucod, 7);
            if(entidades.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sin resultado");
            }

            return ResponseEntity.ok(entidades);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getClass().getSimpleName() + e.getMessage());
        }        
    }
}
