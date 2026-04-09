package com.example.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.MenuDto;
import com.example.backend.sqlserver1.repository.RpmRepository;

@RestController
@RequestMapping("/api/mnucods")
@CrossOrigin(origins = "*")
public class RpmController {

    @Autowired
    private RpmRepository rpmRepository;

    @GetMapping
    public ResponseEntity<?> getMenusBlock(
        @RequestParam String PERCOD
    ) {
        try {

            List<MenuDto> menus = rpmRepository.findByPERCODAndAPLCOD(PERCOD, 7);
            if (menus.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sin resultado");
            }

            return ResponseEntity.ok(menus);
        } catch (DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error: " + ex.getMostSpecificCause().getMessage());
        }
    }
}
