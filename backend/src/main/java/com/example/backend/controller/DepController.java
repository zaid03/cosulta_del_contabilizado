package com.example.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.DepWithCgeView;
import com.example.backend.sqlserver2.repository.DepRepository;

@RestController
@RequestMapping("/api/dep")
public class DepController {
    @Autowired
    private DepRepository depRepository;

    private static final String SIN_RESULTADO = "Sin resultado";
    private static final String ERROR = "Error :";
    private static final String FALTAN = "Faltan datos obligatorios";

    //fetching services for a user (main panel)
    @GetMapping("/fetch-services-persona/{ent}/{eje}/{percod}")
    public ResponseEntity<?> fetchServicesPersona(
        @PathVariable Integer ent,
        @PathVariable String eje,
        @PathVariable String percod
    ) {
        try {
            List<DepWithCgeView> services = depRepository.findByENTAndEJEAndDpes_PERCOD(ent, eje, percod);
            if (services.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SIN_RESULTADO);
            }

            return ResponseEntity.ok(services);
        } catch (DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ERROR + ex.getMostSpecificCause().getMessage());
        }
    }
}